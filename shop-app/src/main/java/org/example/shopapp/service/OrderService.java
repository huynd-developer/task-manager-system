package org.example.shopapp.service;

import org.example.shopapp.entity.CartItem;
import org.example.shopapp.entity.Order;
import org.example.shopapp.entity.OrderItem;
import org.example.shopapp.entity.Product;
import org.example.shopapp.entity.User;
import org.example.shopapp.repository.OrderItemRepository;
import org.example.shopapp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartService cartService,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Order createOrderFromCart(User user, String shippingAddress) {
        List<CartItem> cartItems = cartService.getCartByUserId(user.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (cartItem.getQuantity() > product.getStockQuantity()) {
                throw new RuntimeException(
                        "Sản phẩm '" + product.getName() + "' chỉ còn " +
                                product.getStockQuantity() + " sản phẩm trong kho"
                );
            }
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setStatus("PENDING");

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productService.saveProduct(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemRepository.save(orderItem);
        }

        cartService.clearCart(user.getId());

        return savedOrder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));

        if (!order.getStatus().equals("PENDING") && !order.getStatus().equals("CONFIRMED")) {
            throw new RuntimeException("Chỉ có thể hủy đơn hàng khi đang ở trạng thái 'Chờ xác nhận' hoặc 'Đã xác nhận'");
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);

        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                if (product != null) {
                    int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
                    int returnedQuantity = item.getQuantity() != null ? item.getQuantity() : 0;

                    product.setStockQuantity(currentStock + returnedQuantity);
                    productService.saveProduct(product);
                }
            }
        }

    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        try {
            return orderRepository.findAllByOrderByOrderDateDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public double getTotalRevenue() {
        try {
            Double revenue = orderRepository.getTotalRevenue();
            return revenue != null ? revenue : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public List<Order> getRecentOrders(int count) {
        try {
            List<Order> orders = orderRepository.findTop5ByOrderByOrderDateDesc();
            return orders.stream().limit(count).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
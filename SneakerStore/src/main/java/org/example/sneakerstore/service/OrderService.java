package org.example.sneakerstore.service;
import org.example.sneakerstore.entity.Order;
import org.example.sneakerstore.entity.OrderDetail;
import org.example.sneakerstore.entity.Product;
import org.example.sneakerstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrderByOrderDateDesc();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        // Calculate total amount from order details
        BigDecimal totalAmount = order.getOrderDetails().stream()
                .map(OrderDetail::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public Order createOrder(Order order, List<OrderDetail> orderDetails) {
        // Update product stock
        for (OrderDetail detail : orderDetails) {
            Product product = detail.getProduct();
            if (product.getStockQuantity() < detail.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            product.setStockQuantity(product.getStockQuantity() - detail.getQuantity());
            productService.saveProduct(product);
        }

        order.setOrderDetails(orderDetails);
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(order);
        }

        return saveOrder(order);
    }
}

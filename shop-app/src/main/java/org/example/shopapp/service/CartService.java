package org.example.shopapp.service;

import org.example.shopapp.entity.CartItem;
import org.example.shopapp.entity.Product;
import org.example.shopapp.entity.User;
import org.example.shopapp.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public List<CartItem> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addToCart(User user, Product product, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        CartItem existingItem = cartRepository.findByUserIdAndProductId(user.getId(), product.getId());

        int requestedQuantity = quantity;
        if (existingItem != null) {
            requestedQuantity += existingItem.getQuantity();
        }

        if (requestedQuantity > product.getStockQuantity()) {
            throw new RuntimeException(
                    "Sản phẩm '" + product.getName() + "' chỉ còn " +
                            product.getStockQuantity() + " sản phẩm trong kho. " +
                            "Bạn đã có " + (existingItem != null ? existingItem.getQuantity() : 0) +
                            " sản phẩm trong giỏ."
            );
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartRepository.save(newItem);
        }
    }

    public void updateQuantity(Long userId, Long productId, int newQuantity) {
        if (newQuantity <= 0) {
            removeFromCart(userId, productId);
            return;
        }

        CartItem item = cartRepository.findByUserIdAndProductId(userId, productId);
        if (item == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
        }

        if (newQuantity > item.getProduct().getStockQuantity()) {
            throw new RuntimeException(
                    "Chỉ còn " + item.getProduct().getStockQuantity() +
                            " sản phẩm trong kho"
            );
        }

        item.setQuantity(newQuantity);
        cartRepository.save(item);
    }

    public void removeFromCart(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public void clearCart(Long userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }

    public int getCartCount(String username) {
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) return 0;

        List<CartItem> cartItems = cartRepository.findByUserId(user.getId());
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addToCart(User user, Long productId, int quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));

        addToCart(user, product, quantity);
    }
}
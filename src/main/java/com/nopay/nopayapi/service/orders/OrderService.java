package com.nopay.nopayapi.service.orders;

import com.nopay.nopayapi.dto.orders.OrderItemDTO;
import com.nopay.nopayapi.entity.orders.DiscountCode;
import com.nopay.nopayapi.entity.orders.Order;
import com.nopay.nopayapi.entity.orders.OrderDiscount;
import com.nopay.nopayapi.entity.orders.OrderItem;
import com.nopay.nopayapi.entity.orders.ProductDiscount;
import com.nopay.nopayapi.entity.orders.ProductDiscountId;
import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.orders.OrderRepository;
import com.nopay.nopayapi.repository.orders.ProductDiscountRepository;
import com.nopay.nopayapi.repository.orders.OrderItemRepository;
import com.nopay.nopayapi.repository.orders.DiscountCodeRepository;
import com.nopay.nopayapi.repository.orders.OrderDiscountRepository;
import com.nopay.nopayapi.repository.products.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderDiscountRepository orderDiscountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private ProductDiscountRepository productDiscountRepository;

    @Transactional
    public Order findOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public Order createOrder(Set<OrderItemDTO> items, List<String> discountCodes) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItemDTO itemDTO : items) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.getSizes().forEach(size -> {
                if (size.getDescription().toString().equals(itemDTO.getSizeDescription())) {
                    if (size.getStock() < itemDTO.getQuantity()) {
                        throw new RuntimeException("Not enough stock for product: " + product.getDescription());
                    }
                }
            });

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setSize(product.getSizes().stream()
                    .filter(size -> size.getDescription().toString().equals(itemDTO.getSizeDescription())).findFirst()
                    .get());
            orderItems.add(orderItem);
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(orderItems);

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = BigDecimal.ZERO;
        if (discountCodes != null && !discountCodes.isEmpty()) {
            for (String code : discountCodes) {
                DiscountCode discountCode = discountCodeRepository.findByCode(code)
                        .orElseThrow(() -> new RuntimeException("Invalid discount code"));
                if (discountCode.getIsWholeOrder()) {
                    totalDiscount = discountCode.getDiscountAmount();
                    OrderDiscount orderDiscount = new OrderDiscount(null, order, discountCode.getDiscountAmount(),
                            discountCode.getDescription());
                    orderDiscountRepository.save(orderDiscount);
                } else {
                    for (OrderItem item : orderItems) {
                        Optional<ProductDiscount> productDiscount = productDiscountRepository
                                .findById(
                                        new ProductDiscountId(item.getProduct().getIdProduct(), discountCode.getId()));
                        if (productDiscount.isPresent()) {
                            BigDecimal discount = discountCode.getDiscountAmount()
                                    .multiply(BigDecimal.valueOf(item.getQuantity()));
                            totalDiscount = totalDiscount.add(discount);
                            OrderDiscount orderDiscount = new OrderDiscount(null, order, discount,
                                    discountCode.getDescription());
                            orderDiscountRepository.save(orderDiscount);
                        }
                    }
                }
            }
        }

        order.setTotalPrice(totalPrice.subtract(totalDiscount));
        order = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepository.save(item);
            Product product = item.getProduct();
            product.getSizes().forEach(size -> {
                if (size.getDescription().equals(item.getSize().getDescription())) {
                    size.setStock(size.getStock() - item.getQuantity());
                }
            });
        }

        return order;
    }

    public List<Order> getUserOrders(Integer userId) {
        User user = getAuthenticatedUser();

        if (!user.getId().equals(userId) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }

        return orderRepository.findAll().stream().filter(order -> order.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrders() {
        User user = getAuthenticatedUser();

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }

        return orderRepository.findAll();
    }

    public Order getOrder(Integer orderId) {
        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }

        return order;
    }

    @Transactional
    public Order updateOrder(Integer orderId, Set<OrderItemDTO> items, List<String> discountCodes) {
        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!user.getRole().equals(Role.ADMIN) && !order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        order.getItems().clear();
        orderItemRepository.deleteAllByOrder(order); // Remove existing order items

        if (discountCodes != null && !discountCodes.isEmpty()) {
            order.getDiscounts().clear();
            orderDiscountRepository.deleteAllByOrder(order); // Remove existing discounts
        }

        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItemDTO itemDTO : items) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getStock() < itemDTO.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getDescription());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            // Reduce the stock
            product.setStock(product.getStock() - itemDTO.getQuantity());
            productRepository.save(product);
        }
        order.setItems(orderItems);

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDiscount = BigDecimal.ZERO;

        if (discountCodes != null && !discountCodes.isEmpty()) {
            for (String code : discountCodes) {
                DiscountCode discountCode = discountCodeRepository.findByCode(code)
                        .orElseThrow(() -> new RuntimeException("Invalid discount code"));
                if (discountCode.getIsWholeOrder()) {
                    totalDiscount = discountCode.getDiscountAmount();
                    OrderDiscount orderDiscount = new OrderDiscount(null, order, discountCode.getDiscountAmount(),
                            discountCode.getDescription());
                    orderDiscountRepository.save(orderDiscount);
                } else {
                    for (OrderItem orderItem : orderItems) {
                        Optional<ProductDiscount> productDiscount = productDiscountRepository
                                .findById(new ProductDiscountId(orderItem.getProduct().getIdProduct(),
                                        discountCode.getId()));
                        if (productDiscount.isPresent()) {
                            BigDecimal discount = discountCode.getDiscountAmount()
                                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                            totalDiscount = totalDiscount.add(discount);
                            OrderDiscount orderDiscount = new OrderDiscount(null, order, discount,
                                    discountCode.getDescription());
                            orderDiscountRepository.save(orderDiscount);
                        }
                    }
                }
            }
        }

        order.setTotalPrice(totalPrice.subtract(totalDiscount));

        return orderRepository.save(order);
    }

    public void deleteOrder(Integer orderId) {
        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }

        orderRepository.deleteById(orderId);
    }

    public Order updateOrderStatus(Integer orderId, String status) {
        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }

        if (Order.OrderStatus.valueOf(status) == null) {
            throw new RuntimeException("Invalid status");
        }

        order.setStatus(Order.OrderStatus.valueOf(status));

        return orderRepository.save(order);
    }

    private User getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

package com.nopay.nopayapi.service.orders;

import com.nopay.nopayapi.dto.orders.OrderResponseDTO;
import com.nopay.nopayapi.dto.PaginatedResponse;
import com.nopay.nopayapi.dto.orders.OrderDiscountResponseDTO;
import com.nopay.nopayapi.dto.orders.OrderItemDTO;
import com.nopay.nopayapi.dto.orders.OrderItemResponseDTO;
import com.nopay.nopayapi.entity.orders.*;
import com.nopay.nopayapi.entity.products.Product;
import com.nopay.nopayapi.entity.products.Size;
import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.orders.*;
import com.nopay.nopayapi.repository.products.ProductRepository;
import com.nopay.nopayapi.repository.products.SizeRepository;
import com.nopay.nopayapi.repository.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;

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
    private SizeRepository sizeRepository;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private ProductDiscountRepository productDiscountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order findOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public OrderResponseDTO createOrder(Set<OrderItemDTO> items, List<String> discountCodes) {
        User user = getAuthenticatedUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Create the order and set initial details
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        // Save the order first to generate the ID
        order = orderRepository.save(order);

        // Create order items with the saved order
        Set<OrderItem> orderItems = createOrderItems(items, order);
        order.setItems(orderItems);

        // Calculate total price and apply discounts
        BigDecimal totalPrice = calculateTotalPrice(orderItems);
        order.setTotalPrice(totalPrice);

        if (discountCodes != null && discountCodes.size() > 0) {
            BigDecimal totalDiscount = applyDiscounts(order, discountCodes, orderItems);
            order.setTotalPrice(totalPrice.subtract(totalDiscount));
        }

        // Check stock availability
        if (!checkStockAvailability(items)) {
            throw new RuntimeException("Not enough stock");
        }

        // Assign Status to the order as PENDING
        order.setStatus(OrderStatus.PENDING);

        // Save order items
        orderItemRepository.saveAll(orderItems);

        orderRepository.save(order);
        // Update product stock
        updateProductStock(orderItems);

        return convertToResponseDTO(order);
    }

    private boolean checkStockAvailability(Set<OrderItemDTO> items) {
        for (OrderItemDTO item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            Size size = findProductSize(product, item.getSizeDescription());
            if (size.getStock() < item.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private User getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Ensure user exists in the database
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in the database"));
    }

    public List<OrderResponseDTO> getUserOrders(Integer userId) {
        User user = getAuthenticatedUser();
        validateUserAuthorization(user, userId);

        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .collect(Collectors.toList());

        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderResponseDTOs.add(convertToResponseDTO(order));
        }
        return orderResponseDTOs;
    }

    public List<OrderResponseDTO> getAllOrders() {
        User user = getAuthenticatedUser();
        validateAdminAuthorization(user);

        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderResponseDTOs.add(convertToResponseDTO(order));
        }
        return orderResponseDTOs;
    }

    public Order getOrder(Integer orderId) {
        User user = getAuthenticatedUser();
        Order order = getOrderById(orderId);
        validateOrderAccess(user, order);

        return order;
    }

    @Transactional
    public Order updateOrder(Integer orderId, Set<OrderItemDTO> items, List<String> discountCodes) {
        User user = getAuthenticatedUser();
        Order order = getOrderById(orderId);
        validateOrderAccess(user, order);

        updateOrderItems(order, items);
        updateOrderDiscounts(order, discountCodes);

        BigDecimal totalPrice = calculateTotalPrice(order.getItems());
        BigDecimal totalDiscount = applyDiscounts(order, discountCodes, order.getItems());

        order.setTotalPrice(totalPrice.subtract(totalDiscount));

        return orderRepository.save(order);
    }

    public void deleteOrder(Integer orderId) {
        User user = getAuthenticatedUser();
        Order order = getOrderById(orderId);
        validateOrderAccess(user, order);

        orderRepository.deleteById(orderId);
    }

    public Order updateOrderStatus(Integer orderId, String status) {
        User user = getAuthenticatedUser();
        Order order = getOrderById(orderId);
        validateOrderAccess(user, order);

        order.setStatus(OrderStatus.valueOf(status));

        return orderRepository.save(order);
    }

    private Set<OrderItem> createOrderItems(Set<OrderItemDTO> items, Order order) {
        return items.stream().map(itemDTO -> createOrderItem(itemDTO, order)).collect(Collectors.toSet());
    }

    private OrderItem createOrderItem(OrderItemDTO itemDTO, Order order) {
        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Size size = findProductSize(product, itemDTO.getSizeDescription());

        validateStock(size, itemDTO.getQuantity());

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId(order.getIdOrder(), product.getIdProduct(), size.getIdSize()));
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(itemDTO.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItem.setSize(size);

        return orderItem;
    }

    private Size findProductSize(Product product, String sizeDescription) {
        return product.getSizes().stream()
                .filter(size -> size.getDescription().toString().equals(sizeDescription))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Size not found for product: " + product.getDescription()));
    }

    private void validateStock(Size size, int quantity) {
        if (size.getStock() < quantity) {
            throw new RuntimeException("Not enough stock for size: " + size.getDescription());
        }
    }

    private BigDecimal calculateTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal applyDiscounts(Order order, List<String> discountCodes, Set<OrderItem> orderItems) {
        BigDecimal totalDiscount = BigDecimal.ZERO;

        if (discountCodes != null && !discountCodes.isEmpty()) {
            for (String code : discountCodes) {

                DiscountCode discountCode = discountCodeRepository.findByCode(code)
                        .orElseThrow(() -> new RuntimeException("Invalid discount code"));
                totalDiscount = applyDiscountToOrder(order, discountCode, orderItems, totalDiscount);
            }
        }

        return totalDiscount;
    }

    private BigDecimal applyDiscountToOrder(Order order, DiscountCode discountCode, Set<OrderItem> orderItems,
            BigDecimal totalDiscount) {
        if (discountCode.getIsWholeOrder() && discountCode.getActive()) {
            totalDiscount = discountCode.getDiscountAmount();
            saveOrderDiscount(order, discountCode.getDiscountAmount(), discountCode.getDescription());
        } else {
            totalDiscount = applyProductDiscounts(order, discountCode, orderItems, totalDiscount);
        }
        return totalDiscount;
    }

    private BigDecimal applyProductDiscounts(Order order, DiscountCode discountCode, Set<OrderItem> orderItems,
            BigDecimal totalDiscount) {
        for (OrderItem item : orderItems) {
            Optional<ProductDiscount> productDiscount = productDiscountRepository
                    .findById(new ProductDiscountId(item.getProduct().getIdProduct(), discountCode.getId()));
            if (productDiscount.isPresent() && productDiscount.get().getDiscountCode().getActive()) {
                BigDecimal discount = discountCode.getDiscountAmount().multiply(BigDecimal.valueOf(item.getQuantity()));
                totalDiscount = totalDiscount.add(discount);
                saveOrderDiscount(order, discount, discountCode.getDescription());
            }
        }
        return totalDiscount;
    }

    private void saveOrderDiscount(Order order, BigDecimal discountAmount, String description) {
        OrderDiscount orderDiscount = new OrderDiscount(null, order, discountAmount, description, true);
        orderDiscountRepository.save(orderDiscount);
    }

    private void updateProductStock(Set<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            Size size = item.getSize();
            size.setStock(size.getStock() - item.getQuantity());
            sizeRepository.save(size);
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
    }

    private Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getIdOrder());
        dto.setUser(order.getUser().getEmail());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus().name());

        Set<OrderItemResponseDTO> items = order.getItems().stream().map(item -> new OrderItemResponseDTO(
                item.getProduct().getIdProduct(),
                item.getProduct().getDescription(),
                item.getSize().getDescription().toString(),
                item.getQuantity(),
                item.getPrice())).collect(Collectors.toSet());
        dto.setItems(items);

        Set<OrderDiscountResponseDTO> discounts = new HashSet<>();
        if (order.getDiscounts() != null) {
            discounts = order.getDiscounts().stream()
                    .map(discount -> new OrderDiscountResponseDTO(
                            discount.getDescription(),
                            discount.getDiscountAmount()))
                    .collect(Collectors.toSet());
        }
        dto.setDiscounts(discounts);

        return dto;

    }

    private void validateUserAuthorization(User user, Integer userId) {
        if (!user.getId().equals(userId) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }
    }

    private void validateAdminAuthorization(User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }
    }

    private void validateOrderAccess(User user, Order order) {
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized");
        }
    }

    private void updateOrderItems(Order order, Set<OrderItemDTO> items) {
        order.getItems().clear();
        orderItemRepository.deleteAllByOrder(order);

        Set<OrderItem> orderItems = createOrderItems(items, order);
        order.setItems(orderItems);
        updateProductStock(orderItems);
    }

    private void updateOrderDiscounts(Order order, List<String> discountCodes) {
        if (discountCodes != null && !discountCodes.isEmpty()) {
            order.getDiscounts().clear();
            orderDiscountRepository.deleteAllByOrder(order);
        }
    }

    public PaginatedResponse<OrderResponseDTO> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);

        List<OrderResponseDTO> orders = orderPage.stream()
                .map(order -> convertToResponseDTO(order))
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                orders,
                orderPage.getNumber(),
                orderPage.getTotalPages(),
                orderPage.getTotalElements());
    }

}

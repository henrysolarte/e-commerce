package com.parquesoft.ecommerce.service;

import com.parquesoft.ecommerce.dto.CreateOrderRequest;
import com.parquesoft.ecommerce.dto.OrderResponse;
import com.parquesoft.ecommerce.entity.*;
import com.parquesoft.ecommerce.repository.OrderRepository;
import com.parquesoft.ecommerce.repository.OrderItemRepository;
import com.parquesoft.ecommerce.repository.ProductRepository;
import com.parquesoft.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                       OrderItemRepository orderItemRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse createOrder(String userEmail, CreateOrderRequest request) {
        // Buscar usuario
        UserEntity user = userRepository.findByEmail(userEmail.toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Validar que los items no estén vacíos
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito debe contener al menos un producto");
        }

        // Validar datos de pago
        if (request.getCardNumber() == null || request.getCardNumber().isEmpty()) {
            throw new IllegalArgumentException("Número de tarjeta requerido");
        }

        // Crear entidad de orden
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(generateOrderNumber());
        order.setUser(user);
        order.setSubtotal(request.getSubtotal());
        order.setIva(request.getIva());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        order.setPaymentMethod("CREDIT_CARD");
        order.setCardLastFourDigits(request.getCardNumber().substring(request.getCardNumber().length() - 4));
        order.setCreatedAt(LocalDateTime.now());

        // Procesar items
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            ProductEntity product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + itemRequest.getProductId()));

            // Validar stock disponible
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + product.getName());
            }

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(itemRequest.getUnitPrice());
            orderItem.setTotalPrice(itemRequest.getUnitPrice().multiply(new BigDecimal(itemRequest.getQuantity())));

            orderItems.add(orderItem);
            
            // RESTAR STOCK del producto
            int nuevoStock = product.getStock() - itemRequest.getQuantity();
            product.setStock(nuevoStock);
            productRepository.save(product);
        }

        order.setItems(orderItems);

        // Guardar orden
        OrderEntity savedOrder = orderRepository.save(order);

        // Guardar items
        for (OrderItemEntity item : orderItems) {
            orderItemRepository.save(item);
        }

        return convertToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(String userEmail, String orderNumber) {
        UserEntity user = userRepository.findByEmail(userEmail.toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        OrderEntity order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        // Verificar que la orden pertenece al usuario
        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Acceso denegado a esta orden");
        }

        return convertToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail.toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<OrderEntity> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);
        return orders.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private OrderResponse convertToResponse(OrderEntity order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setUserEmail(order.getUser().getEmail());
        response.setSubtotal(order.getSubtotal());
        response.setIva(order.getIva());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        response.setPaidAt(order.getPaidAt());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setCardLastFourDigits(order.getCardLastFourDigits());

        List<OrderResponse.OrderItemResponse> itemsResponse = order.getItems().stream()
                .map(item -> {
                    OrderResponse.OrderItemResponse itemResp = new OrderResponse.OrderItemResponse();
                    itemResp.setProductId(item.getProduct().getId());
                    itemResp.setProductName(item.getProduct().getName());
                    itemResp.setQuantity(item.getQuantity());
                    itemResp.setUnitPrice(item.getUnitPrice());
                    itemResp.setTotalPrice(item.getTotalPrice());
                    return itemResp;
                }).collect(Collectors.toList());

        response.setItems(itemsResponse);
        return response;
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + new Random().nextInt(1000);
    }
}

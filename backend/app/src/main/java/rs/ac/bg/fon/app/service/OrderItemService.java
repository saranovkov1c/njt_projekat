package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.OrderItemDto;
import rs.ac.bg.fon.app.entity.Client;
import rs.ac.bg.fon.app.entity.Order1;
import rs.ac.bg.fon.app.entity.OrderItem;
import rs.ac.bg.fon.app.entity.Product;
import rs.ac.bg.fon.app.mapper.OrderItemMapper;
import rs.ac.bg.fon.app.repository.OrderItemRepository;
import rs.ac.bg.fon.app.repository.OrderRepository;
import rs.ac.bg.fon.app.repository.ProductRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper,
            OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderItemDto> getAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(orderItemMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<OrderItemDto> getByOrder(Long orderId) {
        Order1 order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order);
        return orderItems.stream()
                .map(orderItemMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public String addOrderItem(OrderItemDto orderItemDto) {
        try {
            Order1 order = orderRepository.findById(orderItemDto.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderItemDto.getOrder().getId()));

            Product product = productRepository.findById(orderItemDto.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + orderItemDto.getProduct().getId()));

            int trazenaKolicina = orderItemDto.getQuantity();

            // Provera zaliha
            if (product.getStock() < trazenaKolicina) {
                return "Nema dovoljno na stanju za '" + product.getName()
                        + "'. Dostupno: " + product.getStock() + ", traženo: " + trazenaKolicina + ".";
            }

            OrderItem orderItem = orderItemMapper.toDomainEntity(orderItemDto);
            orderItem.setId(null); // baza dodeljuje ID
            orderItem.setOrderId(order);
            orderItem.setProductId(product);
            orderItem.setPriceAtPurchase(calculatePrice(product, order.getClientId()));

            orderItemRepository.save(orderItem);

            // Smanji zalihu proizvoda
            product.setStock(product.getStock() - trazenaKolicina);
            productRepository.save(product);

            return "Successfully created an order item!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot create this order item! Error: " + ex.getMessage();
        }
    }

    public String deleteOrderItem(Long orderItemId) {
        try {
            if (orderItemRepository.existsById(orderItemId)) {
                orderItemRepository.deleteById(orderItemId);
                return "Successfully deleted the order item!";
            } else {
                return "Order item with id " + orderItemId + " not found.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this order item! Error: " + ex.getMessage();
        }
    }

    public OrderItemDto getOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);
        return orderItemMapper.toDomainDTO(orderItem);
    }

    public String updateOrderItem(OrderItemDto orderItemDto) {
        try {
            orderItemRepository.findById(orderItemDto.getId())
                    .orElseThrow(() -> new RuntimeException("Order item not found with id: " + orderItemDto.getId()));

            Order1 order = orderRepository.findById(orderItemDto.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderItemDto.getOrder().getId()));

            Product product = productRepository.findById(orderItemDto.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + orderItemDto.getProduct().getId()));

            OrderItem orderItem = orderItemMapper.toDomainEntity(orderItemDto);
            orderItem.setOrderId(order);
            orderItem.setProductId(product);
            orderItem.setPriceAtPurchase(calculatePrice(product, order.getClientId()));

            orderItemRepository.save(orderItem);
            return "Successfully updated the order item!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this order item! Error: " + ex.getMessage();
        }
    }

    /**
     * Racuna cenu proizvoda u trenutku kupovine. Ako je danas rodjendan
     * klijenta koji je vlasnik porudzbine, primenjuje se 15% popusta.
     */
    private double calculatePrice(Product product, Client client) {
        double price = product.getPrice();

        if (client != null && isBirthdayToday(client.getBirthday())) {
            price = price * 0.85; // 15% popust za rodjendan
        }

        return price;
    }

    private boolean isBirthdayToday(Date birthday) {
        if (birthday == null) {
            return false;
        }

        Calendar today = Calendar.getInstance();
        Calendar bday = Calendar.getInstance();
        bday.setTime(birthday);

        return today.get(Calendar.MONTH) == bday.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == bday.get(Calendar.DAY_OF_MONTH);
    }

    public Long nextId() {
        return orderItemRepository.findAll().stream()
                .mapToLong(OrderItem::getId)
                .max()
                .orElse(0L) + 1;
    }
}

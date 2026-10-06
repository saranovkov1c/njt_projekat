package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.OrderItemDto;
import rs.ac.bg.fon.app.entity.OrderItem;

/**
 *
 * @author SARA
 */
@Component
public class OrderItemMapper implements BaseMapper<OrderItemDto, OrderItem> {

    private final OrderMapper orderMapper = new OrderMapper();
    private final ProductMapper productMapper = new ProductMapper();

    @Override
    public OrderItemDto toDomainDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtPurchase(orderItem.getPriceAtPurchase());
        dto.setOrder(orderMapper.toDomainDTO(orderItem.getOrderId()));
        dto.setProduct(productMapper.toDomainDTO(orderItem.getProductId()));
        return dto;
    }

    @Override
    public OrderItem toDomainEntity(OrderItemDto dto) {
        if (dto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPriceAtPurchase(dto.getPriceAtPurchase());
        orderItem.setOrderId(orderMapper.toDomainEntity(dto.getOrder()));
        orderItem.setProductId(productMapper.toDomainEntity(dto.getProduct()));
        return orderItem;
    }

}

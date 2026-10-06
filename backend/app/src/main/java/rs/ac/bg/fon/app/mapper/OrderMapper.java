package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.OrderDto;
import rs.ac.bg.fon.app.entity.Order1;

/**
 *
 * @author SARA
 */
@Component
public class OrderMapper implements BaseMapper<OrderDto, Order1> {

    private final ClientMapper clientMapper = new ClientMapper();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public OrderDto toDomainDTO(Order1 order) {
        if (order == null) {
            return null;
        }
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrdered(order.getOrdered());
        dto.setArrival(order.getArrival());
        dto.setClient(clientMapper.toDomainDTO(order.getClientId()));
        dto.setUser(userMapper.toDomainDTO(order.getUserId()));
        return dto;
    }

    @Override
    public Order1 toDomainEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }
        Order1 order = new Order1();
        order.setId(dto.getId());
        order.setOrdered(dto.getOrdered());
        order.setArrival(dto.getArrival());
        order.setClientId(clientMapper.toDomainEntity(dto.getClient()));
        order.setUserId(userMapper.toDomainEntity(dto.getUser()));
        return order;
    }

}

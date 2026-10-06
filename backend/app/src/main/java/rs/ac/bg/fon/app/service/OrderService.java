package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.ClientDto;
import rs.ac.bg.fon.app.dto.OrderDto;
import rs.ac.bg.fon.app.dto.UserDto;
import rs.ac.bg.fon.app.entity.Client;
import rs.ac.bg.fon.app.entity.Order1;
import rs.ac.bg.fon.app.entity.User;
import rs.ac.bg.fon.app.mapper.OrderMapper;
import rs.ac.bg.fon.app.repository.ClientRepository;
import rs.ac.bg.fon.app.repository.OrderRepository;
import rs.ac.bg.fon.app.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper,
            ClientRepository clientRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public List<OrderDto> getAll() {
        List<Order1> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getClientsOrders(ClientDto clientDto) {
        // Spring Data izvlaci client_id iz prosledjenog entiteta, dovoljno je da ima postavljen id
        List<Order1> orders = orderRepository.findByClientId(new Client(clientDto.getId()));
        return orders.stream()
                .map(orderMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getUsersOrders(UserDto userDto) {
        List<Order1> orders = orderRepository.findByUserId(new User(userDto.getId()));
        return orders.stream()
                .map(orderMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public String addOrder(OrderDto orderDto) {
        try {
            Client client = clientRepository.findById(orderDto.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + orderDto.getClient().getId()));

            Order1 order = orderMapper.toDomainEntity(orderDto);
            order.setClientId(client);

            // User (radnik) je opcion - klijent narucuje sam, bez dodeljenog radnika
            if (orderDto.getUser() != null && orderDto.getUser().getId() != null) {
                User user = userRepository.findById(orderDto.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + orderDto.getUser().getId()));
                order.setUserId(user);
            } else {
                order.setUserId(null);
            }

            orderRepository.save(order);
            return "Created the order successfully!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot create this order! Error: " + ex.getMessage();
        }
    }

    public String deleteOrder(Long orderId) {
        try {
            if (orderRepository.existsById(orderId)) {
                orderRepository.deleteById(orderId);
                return "Deleted the order successfully!";
            } else {
                return "Order with id " + orderId + " not found.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this order! Error: " + ex.getMessage();
        }
    }

    public OrderDto getOrder(Long orderId) {
        Order1 order = orderRepository.findById(orderId).orElse(null);
        return orderMapper.toDomainDTO(order);
    }

    @Transactional
    public String updateOrder(OrderDto orderDto) {
        try {
            Order1 orderIzBaze = orderRepository.findById(orderDto.getId())
                    .orElseThrow(() -> new RuntimeException("Order with ID " + orderDto.getId() + " not found."));

            Client clientIzBaze = clientRepository.findById(orderDto.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client not found."));

            User userIzBaze = userRepository.findById(orderDto.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found."));

            orderIzBaze.setOrdered(orderDto.getOrdered());
            orderIzBaze.setArrival(orderDto.getArrival());
            orderIzBaze.setClientId(clientIzBaze);
            orderIzBaze.setUserId(userIzBaze);

            orderRepository.save(orderIzBaze);
            return "Updated the order successfully!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this order! Error: " + ex.getMessage();
        }
    }

    public OrderDto createAndReturn(OrderDto orderDto) {
        Client client = clientRepository.findById(orderDto.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Order1 order = orderMapper.toDomainEntity(orderDto);
        order.setId(null); // baza dodeljuje
        order.setClientId(client);

        // User (radnik) je opcion - klijent narucuje sam
        if (orderDto.getUser() != null && orderDto.getUser().getId() != null) {
            User user = userRepository.findById(orderDto.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUserId(user);
        } else {
            order.setUserId(null);
        }

        Order1 saved = orderRepository.save(order);
        return orderMapper.toDomainDTO(saved); // vraca sa dodeljenim ID-jem
    }

    public Long nextId() {
        return orderRepository.findAll().stream()
                .mapToLong(Order1::getId)
                .max()
                .orElse(0L) + 1;
    }
}

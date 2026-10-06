package rs.ac.bg.fon.app.controller;

import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.ClientDto;
import rs.ac.bg.fon.app.dto.OrderDto;
import rs.ac.bg.fon.app.dto.UserDto;
import rs.ac.bg.fon.app.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addOrder(@RequestBody OrderDto order) {
        String result = orderService.addOrder(order);
        HttpStatus status = "Created the order successfully!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createOrder(@RequestBody OrderDto order) {
        try {
            OrderDto created = orderService.createAndReturn(order);
            return ResponseEntity.ok(
                    HttpResponse.getResponseWithData(
                            "Created the order successfully!",
                            Map.of("value", created),
                            HttpStatus.OK));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.getResponseWithData(
                            "Cannot create order: " + ex.getMessage(),
                            Map.of("value", ""),
                            HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Found orders!",
                        Map.of("values", orderService.getAll()),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateOrder(@RequestBody OrderDto order) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully updated the order!",
                        Map.of("value", orderService.updateOrder(order)),
                        HttpStatus.OK));
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<Response> findOrderById(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Order found!",
                        Map.of("value", orderService.getOrder(orderId)),
                        HttpStatus.OK));
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteOrder(@RequestBody Long orderId) {
        String result = orderService.deleteOrder(orderId);
        HttpStatus status = "Deleted the order successfully!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @PostMapping("/getClientsOrders")
    public ResponseEntity<Response> getClientsOrders(@RequestBody ClientDto client) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Orders for the client have been found!",
                        Map.of("values", orderService.getClientsOrders(client)),
                        HttpStatus.OK));
    }

    @PostMapping("/getUsersOrders")
    public ResponseEntity<Response> getUsersOrders(@RequestBody UserDto user) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Orders for the user have been found!",
                        Map.of("values", orderService.getUsersOrders(user)),
                        HttpStatus.OK));
    }

    @GetMapping("/nextId")
    public ResponseEntity<Response> nextId() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Next order id",
                        Map.of("value", orderService.nextId()),
                        HttpStatus.OK));
    }
}

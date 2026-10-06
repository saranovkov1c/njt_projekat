package rs.ac.bg.fon.app.controller;

import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.OrderItemDto;
import rs.ac.bg.fon.app.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addOrderItem(@RequestBody OrderItemDto orderItem) {
        String result = orderItemService.addOrderItem(orderItem);

        HttpStatus status = "Successfully created an order item!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found order items!",
                        Map.of("values", orderItemService.getAll()),
                        HttpStatus.OK));
    }

    @GetMapping("/getOrderItem/{orderItemId}")
    public ResponseEntity<Response> findOrderItemById(@PathVariable("orderItemId") Long orderItemId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found the order item!",
                        Map.of("value", orderItemService.getOrderItem(orderItemId)),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateOrderItem(@RequestBody OrderItemDto orderItem) {
        String result = orderItemService.updateOrderItem(orderItem);

        HttpStatus status = "Successfully updated the order item!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Response> getByOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Items for the order have been found!",
                        Map.of("values", orderItemService.getByOrder(orderId)),
                        HttpStatus.OK));
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteOrderItem(@RequestBody Long orderItemId) {
        String result = orderItemService.deleteOrderItem(orderItemId);

        HttpStatus status = "Successfully deleted the order item!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/nextId")
    public ResponseEntity<Response> nextId() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Next order item id",
                        Map.of("value", orderItemService.nextId()),
                        HttpStatus.OK));
    }
}

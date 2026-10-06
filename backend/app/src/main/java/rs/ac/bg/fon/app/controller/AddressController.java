package rs.ac.bg.fon.app.controller;

import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.AddressDto;
import rs.ac.bg.fon.app.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addAddress(@RequestBody AddressDto address) {
        String result = addressService.addAddress(address);

        HttpStatus status = "Successfully created an address!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found addresses!",
                        Map.of("values", addressService.getAll()),
                        HttpStatus.OK));
    }

    @GetMapping("/getAddress/{addressId}")
    public ResponseEntity<Response> findAddressById(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found the address!",
                        Map.of("value", addressService.getAddress(addressId)),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateAddress(@RequestBody AddressDto address) {
        String result = addressService.updateAddress(address);

        HttpStatus status = "Successfully updated the address!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Response> getClientAddresses(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Addresses for the client have been found!",
                        Map.of("values", addressService.getClientAddresses(clientId)),
                        HttpStatus.OK));
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteAddress(@RequestBody Long addressId) {
        String result = addressService.deleteAddress(addressId);

        HttpStatus status = "Successfully deleted the address!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }
}

package rs.ac.bg.fon.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.ClientDto;
import rs.ac.bg.fon.app.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import rs.ac.bg.fon.app.entity.Client;


@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addClient(@RequestBody ClientDto client) {
        String result = clientService.addClient(client);

        if ("This client already has an account!".equals(result)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(HttpResponse.getResponseWithData(result, Map.of("value", result), HttpStatus.CONFLICT));
        }

        if ("Account created successfully!".equals(result)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(HttpResponse.getResponseWithData(result, Map.of("value", result), HttpStatus.OK));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Clients have been found!",
                        Map.of("values", clientService.getAll()),
                        HttpStatus.OK));
    }

    @GetMapping("/getClient/{clientId}")
    public ResponseEntity<Response> findClientById(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "The client has been found!",
                        Map.of("value", clientService.getClient(clientId)),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateClient(@RequestBody ClientDto client) {
        String result = clientService.updateClient(client);
        HttpStatus status = "Updated the client successfully!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/login/{username}/{password}")
public ResponseEntity<ClientDto> login(@PathVariable("username") String username,
                                        @PathVariable("password") String password,
                                        HttpServletRequest request) {
    ClientDto client = clientService.loginAndGet(username, password);
    if (client == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    HttpSession session = request.getSession(true);
    session.setAttribute("role", "CLIENT");
    session.setAttribute("clientId", client.getId());
    return ResponseEntity.ok(client);
}


    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<Response> searchClient(@PathVariable("searchRequest") String searchRequest) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Searching for clients...",
                        Map.of("values", clientService.searchClients(searchRequest)),
                        HttpStatus.OK));
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteClient(@RequestBody Long clientId) {
        String result = clientService.deleteClient(clientId);
        HttpStatus status = "Deleted the client successfully!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }
}

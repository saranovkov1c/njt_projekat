package rs.ac.bg.fon.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.UserDto;
import rs.ac.bg.fon.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addUser(@RequestBody UserDto user) {
        String result = userService.addUser(user);

        HttpStatus status = "Successfully created a user!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found users!",
                        Map.of("values", userService.getAll()),
                        HttpStatus.OK));
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<Response> findUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found the user!",
                        Map.of("value", userService.getUser(userId)),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateUser(@RequestBody UserDto user) {
        String result = userService.updateUser(user);

        HttpStatus status = "Successfully updated the user!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<UserDto> login(@PathVariable String username,
            @PathVariable String password,
            HttpServletRequest request) {
        try {
            UserDto user = userService.login(username, password);
            
            HttpSession session = request.getSession(true); 
            session.setAttribute("role", user.getRole().toString()); 
            session.setAttribute("userId", user.getId());

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteUser(@RequestBody Long userId) {
        String result = userService.deleteUser(userId);

        HttpStatus status = "Successfully deleted the user!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(HttpResponse.getResponse("Uspesno ste se odjavili.", HttpStatus.OK));
    }
}

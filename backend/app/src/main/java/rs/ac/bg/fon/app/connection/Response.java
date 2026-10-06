/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.app.connection;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 *
 * @author SARA
 */
@JsonInclude(NON_NULL)
public class Response {

    private String message;
    private Map<?, ?> data;
    private HttpStatus status;

    public Response(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }

    public Response(String message, Map<?, ?> data, HttpStatus httpStatus) {
        this.message = message;
        this.data = data;
        this.status = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}

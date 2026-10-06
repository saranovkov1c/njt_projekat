package rs.ac.bg.fon.app.controller;

import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.ProductDto;
import rs.ac.bg.fon.app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addProduct(@RequestBody ProductDto product) {
        String result = productService.addProduct(product);

        HttpStatus status = "Successfully created a product!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found products!",
                        Map.of("values", productService.getAll()),
                        HttpStatus.OK));
    }

    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<Response> searchProduct(@PathVariable("searchRequest") String searchRequest) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Searched products:",
                        Map.of("values", productService.searchProducts(searchRequest)),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateProduct(@RequestBody ProductDto product) {
        String result = productService.updateProduct(product);

        HttpStatus status = "Successfully updated the product!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<Response> findProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found the product!",
                        Map.of("value", productService.getProduct(productId)),
                        HttpStatus.OK));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response> getByCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found the products!",
                        Map.of("values", productService.getByCategory(categoryId)),
                        HttpStatus.OK));
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteProduct(@RequestBody Long productId) {
        String result = productService.deleteProduct(productId);

        HttpStatus status = "Successfully deleted the product!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }
}

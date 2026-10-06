package rs.ac.bg.fon.app.controller;

import rs.ac.bg.fon.app.connection.HttpResponse;
import rs.ac.bg.fon.app.connection.Response;
import rs.ac.bg.fon.app.dto.CategoryDto;
import rs.ac.bg.fon.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addCategory(@RequestBody CategoryDto category) {
        String result = categoryService.addCategory(category);

        HttpStatus status = "Successfully created a category!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found categories!",
                        Map.of("values", categoryService.getAll()),
                        HttpStatus.OK));
    }

    @GetMapping("/getCategory/{categoryId}")
    public ResponseEntity<Response> findCategoryById(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Successfully found the category!",
                        Map.of("value", categoryService.getCategory(categoryId)),
                        HttpStatus.OK));
    }

    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<Response> searchCategory(@PathVariable("searchRequest") String searchRequest) {
        return ResponseEntity.ok(
                HttpResponse.getResponseWithData(
                        "Searched categories:",
                        Map.of("values", categoryService.searchCategories(searchRequest)),
                        HttpStatus.OK));
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateCategory(@RequestBody CategoryDto category) {
        String result = categoryService.updateCategory(category);

        HttpStatus status = "Successfully updated the category!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteCategory(@RequestBody Long categoryId) {
        String result = categoryService.deleteCategory(categoryId);

        HttpStatus status = "Successfully deleted the category!".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(HttpResponse.getResponseWithData(result, Map.of("value", result), status));
    }
}

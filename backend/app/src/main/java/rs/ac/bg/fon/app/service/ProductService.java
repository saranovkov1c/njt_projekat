package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.ProductDto;
import rs.ac.bg.fon.app.entity.Category;
import rs.ac.bg.fon.app.entity.Product;
import rs.ac.bg.fon.app.mapper.ProductMapper;
import rs.ac.bg.fon.app.repository.CategoryRepository;
import rs.ac.bg.fon.app.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String searchParam) {
        List<Product> products = productRepository.findByNameStartsWith(searchParam);
        return products.stream()
                .map(productMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        List<Product> products = productRepository.findByCategoryId(category);
        return products.stream()
                .map(productMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public String addProduct(ProductDto productDto) {
        try {
            Category category = categoryRepository.findById(productDto.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDto.getCategory().getId()));

            Product product = productMapper.toDomainEntity(productDto);
            product.setCategoryId(category);

            productRepository.save(product);
            return "Successfully created a product!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot create this product! Error: " + ex.getMessage();
        }
    }

    public String deleteProduct(Long productId) {
        try {
            if (productRepository.existsById(productId)) {
                productRepository.deleteById(productId);
                return "Successfully deleted the product!";
            } else {
                return "Product with id " + productId + " not found.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this product! Error: " + ex.getMessage();
        }
    }

    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return productMapper.toDomainDTO(product);
    }

    public String updateProduct(ProductDto productDto) {
        try {
            productRepository.findById(productDto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productDto.getId()));

            Category category = categoryRepository.findById(productDto.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDto.getCategory().getId()));

            Product product = productMapper.toDomainEntity(productDto);
            product.setCategoryId(category);

            productRepository.save(product);
            return "Successfully updated the product!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this product! Error: " + ex.getMessage();
        }
    }
}

package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.ProductDto;
import rs.ac.bg.fon.app.entity.Product;

/**
 *
 * @author SARA
 */
@Component
public class ProductMapper implements BaseMapper<ProductDto, Product> {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Override
    public ProductDto toDomainDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setForm(product.getForm());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(categoryMapper.toDomainDTO(product.getCategoryId()));
        return dto;
    }

    @Override
    public Product toDomainEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setForm(dto.getForm());
        product.setImageUrl(dto.getImageUrl());
        product.setCategoryId(categoryMapper.toDomainEntity(dto.getCategory()));
        return product;
    }

}

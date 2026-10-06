package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.CategoryDto;
import rs.ac.bg.fon.app.entity.Category;

/**
 *
 * @author SARA
 */
@Component
public class CategoryMapper implements BaseMapper<CategoryDto, Category> {

    // NAPOMENA: Category i Product uzajamno referenciraju jedno drugo
    // (Category ima List<Product>, a Product ima Category). Kada bi ovaj
    // mapper pozivao ProductMapper za svaki proizvod iz liste, a
    // ProductMapper opet pozivao ovaj CategoryMapper za kategoriju tog
    // proizvoda - dobili bismo beskonacnu rekurziju (StackOverflowError).
    // Zbog toga se polje "products" ovde namerno ne popunjava automatski.
    // Ako ti zatreba lista proizvoda uz kategoriju, popuni je rucno u
    // servisnom sloju pomocu ProductMapper-a, npr:
    // categoryDto.setProducts(category.getProductList().stream()
    //         .map(productMapper::toDomainDTO).collect(Collectors.toList()));

    @Override
    public CategoryDto toDomainDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    @Override
    public Category toDomainEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

}

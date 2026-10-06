package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.CategoryDto;
import rs.ac.bg.fon.app.entity.Category;
import rs.ac.bg.fon.app.mapper.CategoryMapper;
import rs.ac.bg.fon.app.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> searchCategories(String searchParam) {
        List<Category> categories = categoryRepository.findByNameStartsWith(searchParam);
        return categories.stream()
                .map(categoryMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public String addCategory(CategoryDto categoryDto) {
        try {
            Category category = categoryMapper.toDomainEntity(categoryDto);
            categoryRepository.save(category);
            return "Successfully created a category!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot create this category! Error: " + ex.getMessage();
        }
    }

    public String deleteCategory(Long categoryId) {
        try {
            if (categoryRepository.existsById(categoryId)) {
                categoryRepository.deleteById(categoryId);
                return "Successfully deleted the category!";
            } else {
                return "Category with id " + categoryId + " not found.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this category! Error: " + ex.getMessage();
        }
    }

    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return categoryMapper.toDomainDTO(category);
    }

    public String updateCategory(CategoryDto categoryDto) {
        try {
            categoryRepository.findById(categoryDto.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryDto.getId()));

            Category category = categoryMapper.toDomainEntity(categoryDto);
            categoryRepository.save(category);
            return "Successfully updated the category!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this category! Error: " + ex.getMessage();
        }
    }
}

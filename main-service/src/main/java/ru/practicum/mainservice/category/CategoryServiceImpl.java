package ru.practicum.mainservice.category;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.dto.NewCategoryDto;
import ru.practicum.mainservice.exceptions.ConflictException;
import ru.practicum.mainservice.exceptions.NotFoundException;

import java.util.List;

import static ru.practicum.mainservice.utils.Util.getPageRequest;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findCategoryById(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d not found.", categoryId)));
        return CategoryMapper.categoryToCategoryDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategories(int from, int size) {
        PageRequest pageRequest = getPageRequest(from, size);
        return CategoryMapper.categoryToCategoryDto(categoryRepository.findAll(pageRequest).toList());
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category newCategory = CategoryMapper.newCategoryDtoToCategory(newCategoryDto);
        try {
            Category saved = categoryRepository.saveAndFlush(newCategory);
            return CategoryMapper.categoryToCategoryDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d not found.", categoryId)));
        category.setName(categoryDto.getName());
        try {
            Category updated = categoryRepository.saveAndFlush(category);
            return CategoryMapper.categoryToCategoryDto(updated);
        } catch (DataAccessException e) {
            throw new ConflictException(e.getMessage(), "The integrity constraint has been violated.");
        }

    }

    @Override
    public void deleteCategory(long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Category with id=%d not found.", categoryId));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The category is not empty.",
                    "The conditions are not met for the requested operation.");
        }
    }
}

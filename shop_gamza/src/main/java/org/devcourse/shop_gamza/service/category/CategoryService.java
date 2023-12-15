package org.devcourse.shop_gamza.service.category;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.devcourse.shop_gamza.domain.category.Category;
import org.devcourse.shop_gamza.repositoy.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.devcourse.shop_gamza.util.ValidationUtils.requireNonNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category save(String name) {
        requireNonNull(name, "카테고리명은 빈 값일 수 없습니다.");

        Category category = Category.builder().name(name).build();
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new EntityNotFoundException("아이디 '%d'에 해당하는 카테고리가 존재하지 않습니다." .formatted(id)));
    }

    @Transactional
    public void deleteById(Long id) {
        requireNonNull(id, "카테고리 아이디는 null일 수 없습니다.");

        if(categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else throw new EntityNotFoundException("아이디 '%d'에 해당하는 카테고리가 존재하지 않습니다." .formatted(id));
    }
}

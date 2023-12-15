package org.devcourse.shop_gamza.controller.api.category;

import lombok.RequiredArgsConstructor;
import org.devcourse.shop_gamza.controller.api.ApiResponse;
import org.devcourse.shop_gamza.controller.api.category.response.CategoryResponse;
import org.devcourse.shop_gamza.domain.category.Category;
import org.devcourse.shop_gamza.service.category.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.devcourse.shop_gamza.util.ValidationUtils.requireNonBlank;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;

    @ResponseStatus(CREATED)
    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestParam String name) {
        requireNonBlank(name, "카테고리명은 빈 값일 수 없습니다.");

        Category saved = categoryService.save(name);
        CategoryResponse data = CategoryResponse.of(saved);

        return ApiResponse.of(CREATED, data);
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> findAllCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryResponse> data = categories.stream()
                .map(CategoryResponse::of)
                .toList();

        return ApiResponse.ok(data);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public ApiResponse deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ApiResponse.of(NO_CONTENT, null);
    }
}

package org.devcourse.shop_gamza.controller.api.category.response;

import org.devcourse.shop_gamza.domain.category.Category;

public record CategoryResponse(Long id, String name) {
    public static CategoryResponse of(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}

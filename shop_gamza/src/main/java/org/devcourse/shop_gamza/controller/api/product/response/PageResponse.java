package org.devcourse.shop_gamza.controller.api.product.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record PageResponse (
        List data,
        Long totalElements,
        Integer totalPages,
        Integer size,
        Integer currentPage,
        String sortProperty,
        String direction
) {
    public static PageResponse of(List data, Page page) {
        String sortProperty = page.getSort().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("기본 정렬 기준이 없습니다."))
                .getProperty();

        return PageResponse.builder()
                .data(data)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .currentPage(page.getNumber())
                .sortProperty(sortProperty)
                .direction(page.getSort().getOrderFor(sortProperty).getDirection().name())
                .build();
    }
}

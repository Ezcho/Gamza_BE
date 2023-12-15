package org.devcourse.shop_gamza.controller.api.product.request;

import jakarta.validation.constraints.PositiveOrZero;
import org.devcourse.shop_gamza.domain.product.SellingType;
import org.devcourse.shop_gamza.domain.product.vo.Money;
import org.devcourse.shop_gamza.domain.product.vo.Stock;
import org.devcourse.shop_gamza.service.product.request.ProductUpdateServiceRequest;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record ProductUpdateRequest (
        @Length(max = 20)
        String name,
        @PositiveOrZero
        Integer price,
        String description,
        SellingType sellingType,
        @PositiveOrZero
        Integer stock,
        Long categoryId,
        MultipartFile coverImage) {

        public ProductUpdateServiceRequest toServiceRequest() {
                return ProductUpdateServiceRequest.builder()
                        .name(Optional.ofNullable(name))
                        .price((price == null)? Optional.empty() : Optional.of(Money.create(price)))
                        .description(Optional.ofNullable(description))
                        .sellingType(Optional.ofNullable(sellingType))
                        .stock((stock == null)? Optional.empty() : Optional.of(Stock.create(stock)))
                        .categoryId(Optional.ofNullable(categoryId))
                        .coverImage((coverImage == null)? Optional.empty() : Optional.of(coverImage))
                        .build();
        }
}

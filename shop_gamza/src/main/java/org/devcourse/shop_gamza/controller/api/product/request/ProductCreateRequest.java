package org.devcourse.shop_gamza.controller.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.devcourse.shop_gamza.domain.product.SellingType;
import org.devcourse.shop_gamza.domain.product.vo.Money;
import org.devcourse.shop_gamza.domain.product.vo.Stock;
import org.devcourse.shop_gamza.service.product.request.ProductCreateServiceRequest;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductCreateRequest(
        @NotBlank @Length(max = 20)
        String name,
        @NotNull @PositiveOrZero
        Integer price,
        String description,
        @NotNull
        SellingType sellingType,
        @NotNull @PositiveOrZero
        Integer stock,
        @NotNull
        Long categoryId,
        MultipartFile coverImage,
        List<MultipartFile> images) {

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .name(name)
                .price(Money.create(price))
                .description(description)
                .sellingType(sellingType)
                .stock(Stock.create(stock))
                .categoryId(categoryId)
                .coverImage(coverImage)
                .images(images)
                .build();
    }
}

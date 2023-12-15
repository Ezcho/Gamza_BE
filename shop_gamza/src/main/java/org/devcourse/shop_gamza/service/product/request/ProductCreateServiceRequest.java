package org.devcourse.shop_gamza.service.product.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.devcourse.shop_gamza.domain.product.SellingType;
import org.devcourse.shop_gamza.domain.product.vo.Money;
import org.devcourse.shop_gamza.domain.product.vo.Stock;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ProductCreateServiceRequest (
        @NotNull @Length(max = 20)
        String name,
        @NotNull @PositiveOrZero
        Money price,
        String description,
        @NotNull
        SellingType sellingType,
        @NotNull @PositiveOrZero
        Stock stock,
        @NotNull
        Long categoryId,
        MultipartFile coverImage,
        List<MultipartFile> images) {
}

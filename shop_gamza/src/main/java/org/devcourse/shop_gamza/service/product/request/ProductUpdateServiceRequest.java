package org.devcourse.shop_gamza.service.product.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.devcourse.shop_gamza.domain.product.SellingType;
import org.devcourse.shop_gamza.domain.product.vo.Money;
import org.devcourse.shop_gamza.domain.product.vo.Stock;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Builder
public record ProductUpdateServiceRequest(
        @Length(max = 20)
        Optional<String> name,
        @PositiveOrZero
        Optional<Money> price,
        Optional<String> description,
        Optional<SellingType> sellingType,
        @PositiveOrZero
        Optional<Stock> stock,
        Optional<Long> categoryId,
        Optional<MultipartFile> coverImage){
}

package org.devcourse.shop_gamza.domain.product.vo;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;

import static org.devcourse.shop_gamza.util.ValidationUtils.*;

@Embeddable
public record Stock(Integer quantity) {
    public Stock(Integer quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public static Stock create(Integer quantity) {
        return new Stock(quantity);
    }

    // @Embeddable을 위해 기본 생성자 필요
    protected Stock() {
        this(0);
    }

    @AssertTrue
    public void validateQuantity(Integer quantity) {
        requireNonNull(quantity, "재고 수량은 null일 수 없습니다.");
        requireNonNegative(quantity, "재고 수량은 음수일 수 없습니다.");
    }
}

package org.devcourse.shop_gamza.domain.product.vo;

import jakarta.persistence.Embeddable;

import static org.devcourse.shop_gamza.util.ValidationUtils.*;

@Embeddable
public record Money (Integer amount) {
    public Money(Integer amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public static Money create(Integer amount) {
        return new Money(amount);
    }

    // @Embeddable을 위해 기본 생성자 필요
    protected Money() {
        this(0);
    }

    private void validateAmount(Integer amount) {
        requireNonNull(amount, "금액은 null일 수 없습니다.");
        requireNonNegative(amount, "금액은 음수일 수 없습니다.");
    }
}

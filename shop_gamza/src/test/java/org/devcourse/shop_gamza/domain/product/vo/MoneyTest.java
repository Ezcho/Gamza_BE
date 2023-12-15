package org.devcourse.shop_gamza.domain.product.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @DisplayName("돈 객체를 생성할 수 있다.")
    @Test
    void create() {
        // given
        Integer amount = 0;

        // when
        Money created = Money.create(amount);

        // then
        assertThat(created).isNotNull();
        assertThat(created.amount()).isEqualTo(amount);
    }

    @DisplayName("금액이 null인 돈 객체는 생성할 수 없다.")
    @Test
    void createWithNull() {
        // given
        Integer nullValue = null;

        // when then
        assertThatThrownBy(() -> Money.create(nullValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("금액은 null일 수 없습니다.");
    }

    @DisplayName("금액이 음수인 돈 객체는 생성할 수 없다.")
    @Test
    void createWithNegativeAmount() {
        // given
        Integer negativeAmount = -100;

        // when then
        assertThatThrownBy(() -> Money.create(negativeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("금액은 음수일 수 없습니다.");
    }
}
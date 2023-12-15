package org.devcourse.shop_gamza.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @DisplayName("입력값이 null이 아닌 경우 예외가 발생하지 않는다.")
    @Test
    void isNonNullWithNonNull() {
        // given
        Object nonNullValue = new Object();
        String message = "null일 수 없습니다.";

        // when then
        assertDoesNotThrow(
                () -> ValidationUtils.requireNonNull(nonNullValue, message));
    }

    @DisplayName("입력값이 null인 경우 예외가 발생한다.")
    @Test
    void isNonNullWithNull() {
        // given
        Object nullValue = null;
        String message = "null일 수 없습니다.";

        // when then
        assertThatThrownBy(
                () -> ValidationUtils.requireNonNull(nullValue, message))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @DisplayName("입력값이 음수가 아닌 경우 예외가 발생하지 않는다.")
    @Test
    void isNonNegativeWithNonNegative() {
        // given
        Integer nonNegative = 0;
        String message = "음수일 수 없습니다.";

        // when then
        assertDoesNotThrow(
                () -> ValidationUtils.requireNonNegative(nonNegative, message));
    }

    @DisplayName("입력값이 음수인 경우 예외가 발생한다.")
    @Test
    void isNonNegativeWithNegative() {
        // given
        Integer negative = -1;
        String message = "음수일 수 없습니다.";

        // when then
        assertThatThrownBy(
                () -> ValidationUtils.requireNonNegative(negative, message))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }
}
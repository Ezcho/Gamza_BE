package org.devcourse.shop_gamza.domain.product.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.zip.Adler32;
import java.util.zip.CRC32;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {
    @DisplayName("재고 객체를 생성할 수 있다.")
    @Test
    void create() {
        // given
        Integer quantity = 0;

        // when
        Stock created = Stock.create(quantity);

        // then
        assertThat(created).isNotNull();
        assertThat(created.quantity()).isEqualTo(quantity);
    }

    @DisplayName("재고 수량이 null인 재고 객체는 생성할 수 없다.")
    @Test
    void createWithNull() {
        // given
        Integer nullQuantity = null;

        // when then
        assertThatThrownBy(() -> Stock.create(nullQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량은 null일 수 없습니다.");
    }

    @DisplayName("재고 수량이 음수인 재고 객체는 생성할 수 없다.")
    @Test
    void createWithNegativeQuantity() {
        // given
        Integer negativeQuantity = -100;

        // when then
        assertThatThrownBy(() -> Stock.create(negativeQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량은 음수일 수 없습니다.");
    }


    @Test
    void crc32Test() {
        // given
        Long longId = 1_000_000_000L;

        // whe
        long crced = crc32(longId);
        System.out.println(crced);
        long crced2 = crc32(longId);

        assertThat(crced).isEqualTo(crced2);
    }

    @Test
    void adler32Test() {
        // given
        Long longId = 1_000_000_000_123L;

        // whe
        long adler = adler32(longId);
        System.out.println("adler" + adler);
        long adler2 = adler32(longId);

        assertThat(adler2).isEqualTo(adler);
    }

    private long crc32(Long value) {
        CRC32 crc32 = new CRC32();
        crc32.update(value.byteValue());
        return crc32.getValue();
    }

    private long adler32(Long value) {
        Adler32 adler32 = new Adler32();
        adler32.update(value.byteValue());
        return adler32.getValue();
    }
}

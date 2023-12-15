package org.devcourse.shop_gamza.domain.category;

import jakarta.persistence.*;
import lombok.*;
import org.devcourse.shop_gamza.domain.BaseTimeEntity;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;
}

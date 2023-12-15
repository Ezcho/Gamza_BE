package org.devcourse.shop_gamza.domain.image;

import jakarta.persistence.*;
import lombok.*;
import org.devcourse.shop_gamza.domain.BaseTimeEntity;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String uploadFileName;

    private String storeFileName;
}

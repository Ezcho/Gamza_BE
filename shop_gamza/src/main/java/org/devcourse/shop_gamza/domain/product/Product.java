package org.devcourse.shop_gamza.domain.product;

import jakarta.persistence.*;
import lombok.*;
import org.devcourse.shop_gamza.domain.BaseTimeEntity;
import org.devcourse.shop_gamza.domain.category.Category;
import org.devcourse.shop_gamza.domain.image.Image;
import org.devcourse.shop_gamza.domain.product.vo.Money;
import org.devcourse.shop_gamza.domain.product.vo.Stock;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price"))
    })
    private Money price;

    private String description;

    @Enumerated(EnumType.STRING)
    private SellingType sellingType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "quantity", column = @Column(name = "stock"))
    })
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();


    public void addImage(Image image) {
        ProductImage productImage = ProductImage.builder()
                .product(this)
                .image(image)
                .build();

        this.images.add(productImage);
    }

    public void changeName(String name) {
        this.name = name;
    }

}

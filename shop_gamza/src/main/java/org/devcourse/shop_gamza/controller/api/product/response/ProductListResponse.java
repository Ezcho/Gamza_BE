package org.devcourse.shop_gamza.controller.api.product.response;

import lombok.Builder;
import org.devcourse.shop_gamza.controller.api.image.response.ImageResponse;
import org.devcourse.shop_gamza.domain.image.Image;
import org.devcourse.shop_gamza.domain.product.Product;
import org.devcourse.shop_gamza.domain.product.SellingType;

@Builder
public record ProductListResponse(
        Long id,
        String name,
        Integer price,
        SellingType sellingType,
        ImageResponse coverImage
) {
    public static ProductListResponse of(Product product) {
        ProductListResponseBuilder builder = ProductListResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice().amount())
                .sellingType(product.getSellingType());

        Image coverImage = product.getCoverImage();
        if (coverImage != null) {
            ImageResponse imageResponse = new ImageResponse(coverImage.getId(), coverImage.getStoreFileName());
            builder.coverImage(imageResponse);
        }

        return builder.build();
    }
}

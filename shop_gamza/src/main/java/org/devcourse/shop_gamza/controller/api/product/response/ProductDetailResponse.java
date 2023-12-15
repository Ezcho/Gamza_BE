package org.devcourse.shop_gamza.controller.api.product.response;

import lombok.Builder;
import org.devcourse.shop_gamza.controller.api.image.response.ImageResponse;
import org.devcourse.shop_gamza.domain.category.Category;
import org.devcourse.shop_gamza.domain.image.Image;
import org.devcourse.shop_gamza.domain.product.Product;
import org.devcourse.shop_gamza.domain.product.ProductImage;
import org.devcourse.shop_gamza.domain.product.SellingType;

import java.util.List;

@Builder
public record ProductDetailResponse(
        Long id,
        String name,
        Integer price,
        String description,
        SellingType sellingType,
        Integer stock,
        Category category,
        Image coverImage,
        List<ImageResponse> images) {

    public static ProductDetailResponse of(Product product) {
        List<ImageResponse> imageList = product.getImages().stream()
                .map(ProductImage::getImage)
                .map(image -> new ImageResponse(image.getId(), image.getStoreFileName()))
                .toList();

        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice().amount())
                .description(product.getDescription())
                .sellingType(product.getSellingType())
                .stock(product.getStock().quantity())
                .category(product.getCategory())
                .coverImage(product.getCoverImage())
                .images(imageList)
                .build();
    }
}

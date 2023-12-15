package org.devcourse.shop_gamza.controller.api.product;

import lombok.RequiredArgsConstructor;
import org.devcourse.shop_gamza.controller.api.ApiResponse;
import org.devcourse.shop_gamza.controller.api.product.request.ProductCreateRequest;
import org.devcourse.shop_gamza.controller.api.product.request.ProductUpdateRequest;
import org.devcourse.shop_gamza.controller.api.product.response.PageResponse;
import org.devcourse.shop_gamza.controller.api.product.response.ProductDetailResponse;
import org.devcourse.shop_gamza.controller.api.product.response.ProductListResponse;
import org.devcourse.shop_gamza.domain.product.Product;
import org.devcourse.shop_gamza.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @ResponseStatus(CREATED)
    @PostMapping
    public ApiResponse<ProductDetailResponse> createProduct(@Validated @ModelAttribute ProductCreateRequest request) {
        Product saved = productService.save(request.toServiceRequest());
        ProductDetailResponse data = ProductDetailResponse.of(saved);

        return ApiResponse.of(CREATED, data);
    }

    @GetMapping
    public ApiResponse<PageResponse> findAllProducts(@PageableDefault(size = 8, sort = "createdAt", direction = DESC) Pageable pageable) {
        Page<Product> page = productService.findAll(pageable);
        List<ProductListResponse> products = page.getContent().stream()
                .map(ProductListResponse::of)
                .toList();

        PageResponse data = PageResponse.of(products, page);

        return ApiResponse.ok(data);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDetailResponse> findProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        ProductDetailResponse data = ProductDetailResponse.of(product);

        return ApiResponse.ok(data);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updateProduct(@PathVariable Long id, @Validated @ModelAttribute ProductUpdateRequest request) {
        Long productId = productService.updateProduct(id, request.toServiceRequest());
        return ApiResponse.ok(productId);
    }
}

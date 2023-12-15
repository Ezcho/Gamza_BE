package org.devcourse.shop_gamza.service.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.devcourse.shop_gamza.domain.category.Category;
import org.devcourse.shop_gamza.domain.image.Image;
import org.devcourse.shop_gamza.domain.product.Product;
import org.devcourse.shop_gamza.repositoy.product.ProductRepository;
import org.devcourse.shop_gamza.service.FileService;
import org.devcourse.shop_gamza.service.category.CategoryService;
import org.devcourse.shop_gamza.service.product.request.ProductCreateServiceRequest;
import org.devcourse.shop_gamza.service.product.request.ProductUpdateServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final FileService fileService;

    @Transactional
    public Product save(@Validated ProductCreateServiceRequest request) {
        Category category = categoryService.findById(request.categoryId());

        Image coverImage = fileService.storeFile(request.coverImage());
        List<Image> images = fileService.storeFiles(request.images());

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .sellingType(request.sellingType())
                .stock(request.stock())
                .category(category)
                .coverImage(coverImage)
                .build();

        for (Image image : images) {
            product.addImage(image);
        }

        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        page.getContent().stream().forEach(p -> {
            if (p.getCoverImage() != null) {
                p.getCoverImage().getId(); // 강제 즉시 로딩
            }
        });

        return page;
    }

    public Product findById(Long id) {
        return productRepository.findByIdWithCoverImageAndCategoryAndImages(id)
                .orElseThrow(() -> new EntityNotFoundException("아이디 '%d'에 해당하는 상품이 존재하지 않습니다.".formatted(id)));
    }

    @Transactional
    public Long updateProduct(Long id, @Validated ProductUpdateServiceRequest request) {
        Product product = findById(id);

        request.coverImage().ifPresent((image) -> {
            Image saved = fileService.storeFile(image);
            product.setCoverImage(saved);
        });

        request.name().ifPresent(product::setName);
        request.price().ifPresent(product::setPrice);
        request.description().ifPresent(product::setDescription);
        request.sellingType().ifPresent(product::setSellingType);
        request.stock().ifPresent(product::setStock);
        request.categoryId().ifPresent((categoryId) -> {
            Category category = categoryService.findById(categoryId);
            product.setCategory(category);
        });

        return product.getId();
    }
}

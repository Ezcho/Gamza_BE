package org.devcourse.shop_gamza.repositoy.product;

import org.devcourse.shop_gamza.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.coverImage " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.images i " +
            "LEFT JOIN FETCH i.image " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithCoverImageAndCategoryAndImages(Long id);
}

package org.devcourse.shop_gamza.repositoy.category;

import org.devcourse.shop_gamza.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

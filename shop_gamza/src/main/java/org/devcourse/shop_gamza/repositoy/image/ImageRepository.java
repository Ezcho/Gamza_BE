package org.devcourse.shop_gamza.repositoy.image;

import org.devcourse.shop_gamza.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

package org.devcourse.shop_gamza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShopGamzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopGamzaApplication.class, args);
	}
}

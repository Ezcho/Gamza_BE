DROP DATABASE IF EXISTS gamza_database;
DROP DATABASE IF EXISTS test_gamza_database;

CREATE DATABASE gamza_database;
CREATE DATABASE test_gamza_database;

USE gamza_database;

DROP TABLE IF EXISTS product_image;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user;

CREATE TABLE category
(
    category_id BIGINT AUTO_INCREMENT,
    name        VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_category_id PRIMARY KEY (category_id)
);

CREATE TABLE image
(
    image_id         BIGINT AUTO_INCREMENT,
    upload_file_name VARCHAR(255),
    store_file_name  VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_image_id PRIMARY KEY (image_id)
);

CREATE TABLE product
(
    product_id     BIGINT AUTO_INCREMENT,
    name           VARCHAR(20) NOT NULL,
    price          INT         NOT NULL,
    description    TEXT,
    selling_type   VARCHAR(20) NOT NULL,
    stock          INT         NOT NULL,
    category_id    BIGINT      NOT NULL,
    cover_image_id BIGINT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_product_id PRIMARY KEY (product_id),
    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id) REFERENCES category (category_id),
    CONSTRAINT fk_cover_image_id
        FOREIGN KEY (cover_image_id) REFERENCES image (image_id)
);

CREATE TABLE product_image
(
    product_image_id BIGINT AUTO_INCREMENT,
    product_id       BIGINT NOT NULL,
    image_id         BIGINT NOT NULL,
    CONSTRAINT pk_product_image_id PRIMARY KEY (product_image_id),
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT fk_image_id
        FOREIGN KEY (image_id) REFERENCES image (image_id)
);

CREATE TABLE user
(
    user_id      BIGINT AUTO_INCREMENT,
    login_id     VARCHAR(20)  NOT NULL,
    password     VARCHAR(255) NOT NULL,
    type         VARCHAR(10) DEFAULT 'USER',
    name         VARCHAR(20),
    phone_number INT(11),
    CONSTRAINT pk_user_id PRIMARY KEY (user_id),
    CONSTRAINT uc_login_id UNIQUE (login_id)
);

#  테스트 데이터 베이스 DDL
USE test_gamza_database;

DROP TABLE IF EXISTS product_image;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS category;

CREATE TABLE category
(
    category_id BIGINT AUTO_INCREMENT,
    name        VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_category_id PRIMARY KEY (category_id)
);

CREATE TABLE image
(
    image_id         BIGINT AUTO_INCREMENT,
    upload_file_name VARCHAR(255),
    store_file_name  VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_image_id PRIMARY KEY (image_id)
);

CREATE TABLE product
(
    product_id   BIGINT AUTO_INCREMENT,
    name         VARCHAR(20) NOT NULL,
    price        INT         NOT NULL,
    description  TEXT,
    selling_type VARCHAR(20) NOT NULL,
    stock        INT         NOT NULL,
    category_id  BIGINT      NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_product_id PRIMARY KEY (product_id),
    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE product_image
(
    product_image_id BIGINT AUTO_INCREMENT,
    product_id       BIGINT NOT NULL,
    image_id         BIGINT NOT NULL,
    CONSTRAINT pk_product_image_id PRIMARY KEY (product_image_id),
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT fk_image_id
        FOREIGN KEY (image_id) REFERENCES image (image_id)
);

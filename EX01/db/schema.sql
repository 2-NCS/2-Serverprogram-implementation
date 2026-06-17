-- S-Product 상품 관리 스키마 (MySQL testdb / localhost:3306 / root / 1234)
DROP TABLE IF EXISTS tbl_product;

CREATE TABLE tbl_product (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    price     INT          NOT NULL,
    stock     INT          NOT NULL,
    category  VARCHAR(50),
    create_at TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- seed 3건
INSERT INTO tbl_product VALUES (null, '기계식 키보드', 89000,  30, 'PC주변기기', now());
INSERT INTO tbl_product VALUES (null, '무선 마우스',   29000, 100, 'PC주변기기', now());
INSERT INTO tbl_product VALUES (null, '27인치 모니터', 245000, 15, '디스플레이', now());

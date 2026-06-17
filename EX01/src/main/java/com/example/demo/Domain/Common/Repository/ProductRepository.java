package com.example.demo.Domain.Common.Repository;

import com.example.demo.Domain.Common.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // TODO: 등록 시 상품명 중복 검사용 쿼리메서드를 선언하라.
    //       (힌트: boolean existsByName(String name) — 선언만 하면 구현은 자동 생성)
    boolean existsByName(String name);

    // TODO: 수정 시 자기 자신(id)을 제외한 상품명 중복 검사용 쿼리메서드를 선언하라.
    //       (힌트: boolean existsByNameAndIdNot(String name, Long id))
    boolean existsByNameAndIdNot(String name, Long id);

    // TODO(배치): 품절(stock=0) 상품 건수 조회용 쿼리메서드를 선언하라.
    //       (힌트: long countByStock(int stock))
    long countByStock(int stock);

}

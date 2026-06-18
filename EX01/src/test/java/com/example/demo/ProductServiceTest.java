package com.example.demo;

import com.example.demo.Domain.Common.Dtos.ProductDTO;
import com.example.demo.Domain.Common.Repository.ProductRepository;
import com.example.demo.Domain.Common.Service.ProductService;
import com.example.demo.Exception.MyBizException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional   // 테스트 후 자동 롤백
class ProductServiceTest {

    @Autowired private ProductService productService;
    @Autowired private ProductRepository productRepository;

    // TODO: 정상 등록 테스트
    //  - ProductDTO 빌더로 유효한 상품 생성 → register → id 가 생성되었는지 assertNotNull
    @Test
    void register_정상() {
        ProductDTO dto = productService.register(ProductDTO.builder()
                .name("A")
                .price(1000)
                .stock(10)
                .category("AA")
                .build());
        assertNotNull(dto.getId());
    }

    // TODO: 일괄등록 중간 실패 시 전체 롤백 테스트
    //  - 먼저 상품 1건 등록(중복 유발용 이름)
    //  - bulk 리스트 2건 중 2번째를 위 이름과 중복으로 구성
    //  - registerBulk 호출 시 MyBizException 발생(assertThrows)
    //  - 전체 count 가 (등록 1건) 그대로인지(=bulk 0건 저장) 확인
    @Test
    void registerBulk_중간실패_전체롤백() {
        productService.register(ProductDTO.builder()
                .name("A")
                .price(1000)
                .stock(10)
                .category("AA")
                .build());
        List<ProductDTO> list = new ArrayList<>();
        list.add(ProductDTO.builder()
                .name("B")
                .price(1000)
                .stock(10)
                .category("BB")
                .build());
        list.add(ProductDTO.builder()
                .name("A")
                .price(1000)
                .stock(10)
                .category("AA")
                .build());
        assertThrows(MyBizException.class,()->{System.out.println("등록 수"+ productService.registerBulk(list));});
        assertEquals(1L, productRepository.count());
    }
}

package com.example.demo;

import com.example.demo.Batch.ProductBatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductBatchTest {

    @Autowired
    private ProductBatch productBatch;

    // TODO: 배치 동작 테스트 (NCS 4.3)
    //  - productBatch.checkSoldOut() 호출
    //  - 반환된 품절 건수가 0 이상인지 검증(assertTrue(count >= 0))
    @Test
    void checkSoldOut_동작() {
        fail("TODO: checkSoldOut 배치 테스트 구현");
    }
}

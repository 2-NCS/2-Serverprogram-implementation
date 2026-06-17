package com.example.demo.Domain.Common.Service;

import com.example.demo.Domain.Common.Dtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    ProductDTO register(ProductDTO dto);
    ProductDTO modify(Long id, ProductDTO dto);
    void remove(Long id);
    ProductDTO get(Long id);
    Page<ProductDTO> list(Pageable pageable);
    int registerBulk(List<ProductDTO> list);   // 한 트랜잭션, 중간 실패 시 전체 롤백
}

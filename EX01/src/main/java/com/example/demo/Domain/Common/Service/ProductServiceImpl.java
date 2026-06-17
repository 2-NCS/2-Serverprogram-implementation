package com.example.demo.Domain.Common.Service;

import com.example.demo.Domain.Common.Dtos.ProductDTO;
import com.example.demo.Domain.Common.Entity.Product;
import com.example.demo.Domain.Common.Repository.ProductRepository;
import com.example.demo.Exception.MyBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // TODO: 상품 등록
    //  - 메서드 위에 @Transactional 적용
    //  - productRepository.existsByName(이름) 으로 중복 검사 → 중복이면 MyBizException 발생
    //  - dto.setCreateAt(LocalDateTime.now()) 설정 후 productRepository.save(dto.toEntity())
    //  - 저장된 Entity 를 ProductDTO.from(...) 으로 변환하여 반환
    @Override
    @Transactional
    public ProductDTO register(ProductDTO dto) {
        if(productRepository.existsByName(dto.getName())) throw new MyBizException("중복된 상품 이름입니다.");
        dto.setCreateAt(LocalDateTime.now());
        return ProductDTO.from(productRepository.save(dto.toEntity()));
    }

    // TODO: 상품 수정
    //  - @Transactional 적용
    //  - productRepository.findById(id) (없으면 MyBizException)
    //  - existsByNameAndIdNot(이름, id) 로 자기 자신 제외 중복 검사
    //  - 조회한 Entity 의 필드를 dto 값으로 변경 → JPA 변경감지(dirty checking)로 update
    //  - 변경된 Entity 를 ProductDTO.from 으로 반환
    @Override
    @Transactional
    public ProductDTO modify(Long id, ProductDTO dto) {
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()) throw new MyBizException("존재하지 않는 상품id입니다.");
        if(productRepository.existsByNameAndIdNot(dto.getName(),id)) throw new MyBizException("동일한 상품이 존재합니다.");
        p.get().setName(dto.getName());
        p.get().setCategory(dto.getCategory());
        p.get().setStock(dto.getStock());
        p.get().setPrice(dto.getPrice());
        p.get().setCreateAt(LocalDateTime.now());

        return ProductDTO.from(p.get());
    }

    // TODO: 상품 삭제
    //  - @Transactional 적용
    //  - productRepository.existsById(id) 확인 (없으면 MyBizException)
    //  - productRepository.deleteById(id)
    @Override
    @Transactional
    public void remove(Long id) {
        if(!productRepository.existsById(id)) throw new MyBizException("존재하지 않는 상품id입니다.");
        productRepository.deleteById(id);
    }

    // TODO: 단건 조회
    //  - @Transactional(readOnly = true) 적용
    //  - productRepository.findById(id) (없으면 MyBizException) → ProductDTO.from 으로 반환
    @Override
    public ProductDTO get(Long id) {
        throw new UnsupportedOperationException("TODO: get 구현");
    }

    // TODO: 목록 조회(페이징)
    //  - @Transactional(readOnly = true) 적용
    //  - productRepository.findAll(pageable) 결과를 .map(ProductDTO::from) 으로 변환하여 반환
    @Override
    public Page<ProductDTO> list(Pageable pageable) {
        throw new UnsupportedOperationException("TODO: list 구현");
    }

    // TODO: 일괄 등록 (한 트랜잭션)
    //  - @Transactional 적용 (중간 실패 시 부분 저장이 남으면 안 됨)
    //  - list 를 순회하며 저장, 하나라도 existsByName 중복이면 MyBizException 발생 → 전체 롤백
    //  - 저장한 건수(int)를 반환
    @Override
    public int registerBulk(List<ProductDTO> list) {
        throw new UnsupportedOperationException("TODO: registerBulk 구현");
    }
}

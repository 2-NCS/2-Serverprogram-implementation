package com.example.demo.Controller;

import com.example.demo.Domain.Common.Dtos.ProductDTO;
import com.example.demo.Domain.Common.Service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/products")
@CrossOrigin(originPatterns = {"http://localhost:[*]"})
public class ProductRestController {

    @Autowired
    private ProductService productService;

    // TODO: 목록 조회
    //  - productService.list(pageable) 호출
    //  - 결과의 content / number / totalPages / totalElements 를 Map 에 담아
    //    ResponseEntity 로 200(OK) 반환
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> list(Pageable pageable) {

        Page<ProductDTO> page = productService.list(pageable);

        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("content", page.getContent());
        responseMap.put("number", page.getNumber());
        responseMap.put("totalPages", page.getTotalPages());
        responseMap.put("totalElements", page.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    // TODO: 단건 조회
    //  - productService.get(id) 결과를 200(OK) 으로 반환
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> get(@PathVariable("id") Long id) {

        ProductDTO dto = productService.get(id);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // TODO: 등록
    //  - @RequestBody @Valid ProductDTO + BindingResult 로 검증
    //  - bindingResult.hasErrors() 이면 FieldError 들을 돌며 필드별 메시지를 Map 에 담아 400(BAD_REQUEST) 반환
    //  - 통과 시 productService.register(dto) 호출 후 200 + "상품 등록 성공!" 메시지 반환
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> add(@RequestBody @Valid ProductDTO dto,
                                                    BindingResult bindingResult) {

        Map<String,Object> responseMap = new HashMap<>();

        // 유효성 검증
        if(bindingResult.hasErrors()){
            for(FieldError error : bindingResult.getFieldErrors()){
                responseMap.put(
                        error.getField(),
                        error.getDefaultMessage()
                );
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseMap);
        }

        // 서비스 실행
        productService.register(dto);

        // 성공 응답
        responseMap.put("message", "상품 등록 성공!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMap);

    }

    // TODO: 수정
    //  - @PathVariable id + @RequestBody @Valid ProductDTO + BindingResult
    //  - 검증 오류 처리는 등록과 동일 / 통과 시 productService.modify(id, dto) → 200 + "상품 수정 성공!"
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id,
                                                       @RequestBody @Valid ProductDTO dto,
                                                       BindingResult bindingResult) {

        Map<String, Object> responseMap = new HashMap<>();

        // 유효성 검증
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                responseMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }

        // 수정
        productService.modify(id, dto);

        // 성공 응답
        responseMap.put("message", "상품 수정 성공!");

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    // TODO: 삭제
    //  - productService.remove(id) 호출 후 200 + "상품 삭제 성공!" 메시지 반환
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        throw new UnsupportedOperationException("TODO: delete 구현");
    }

    // TODO: 일괄 등록 (한 트랜잭션, 중간 실패 시 전체 롤백)
    //  - @RequestBody List<ProductDTO>
    //  - productService.registerBulk(list) 호출 후 등록 건수(count) + "일괄 등록 성공!" 반환
    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addBulk(@RequestBody List<ProductDTO> list) {
        throw new UnsupportedOperationException("TODO: addBulk 구현");
    }
}

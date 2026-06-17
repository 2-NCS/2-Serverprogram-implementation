package com.example.demo.Domain.Common.Dtos;

import com.example.demo.Domain.Common.Entity.Product;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDTO {

    // ===== 속성(필드) + 검증 규칙 : 제공 =====
    private Long id;

    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String name;

    @NotNull(message = "가격은 필수 항목입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "재고는 필수 항목입니다.")
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;

    @NotBlank(message = "카테고리는 필수 항목입니다.")
    private String category;

    private LocalDateTime createAt;

    // ===== 기능(메서드) : 학생 구현 =====

    // TODO: DTO → Entity 변환 메서드를 구현하라.
    //  - Product.builder() 로 id/name/price/stock/category/createAt 를 매핑하여 Product 객체를 반환한다.
    public Product toEntity() {
//        throw new UnsupportedOperationException("TODO: toEntity 구현");
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .category(this.category)
                .createAt(this.createAt)
                .build();
    }

    // TODO: Entity → DTO 변환(static) 메서드를 구현하라.
    //  - ProductDTO.builder() 로 Product 의 각 필드를 매핑하여 ProductDTO 객체를 반환한다.
    public static ProductDTO from(Product p) {
//        throw new UnsupportedOperationException("TODO: from 구현");
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .stock(p.getStock())
                .category(p.getCategory())
                .createAt(p.getCreateAt())
                .build();
    }
}

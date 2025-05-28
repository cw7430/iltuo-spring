package kr.co.iltuo.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class ProductDataResponseDto {
    private Long productId;
    private Long majorCategoryId;
    private Long minerCategoryId;
    private String productCode;
    private String productName;
    private String productComments;
    private long price;
    private long discountedPrice;
    private long optionCount;
    private int discountedRate;
    private boolean recommended;
    private Instant registerDate;
}

package kr.co.iltuo.dto.response.order;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class CartDataResponseDto {
    private Long cartId;
    private Long productId;
    private String productName;
    private String productCode;
    private Long userIdx;
    private long price;
    private int quantity;
}

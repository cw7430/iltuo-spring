package kr.co.iltuo.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CartOptionDataResponseDto {
    private Long cartId;
    private Long optionDetailId;
    private Long priorityIndex;
    private String optionName;
    private String optionDetailName;
    private long optionPrice;
}

package kr.co.iltuo.dto.response.order;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class OrderOptionDataResponseDto {
    private Long orderId;
    private Long priorityIndex;
    private String optionName;
    private String optionDetailName;
    private long optionFluctuatingPrice;
}

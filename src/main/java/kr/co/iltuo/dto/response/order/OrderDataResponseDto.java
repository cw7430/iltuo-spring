package kr.co.iltuo.dto.response.order;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderDataResponseDto {
    private Long orderId;
    private Long paymentId;
    private String productName;
    private int quantity;
    private long price;
    private List<OrderOptionDataResponseDto> orderOptions;
}

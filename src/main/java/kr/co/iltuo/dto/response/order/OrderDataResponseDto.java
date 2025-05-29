package kr.co.iltuo.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderDataResponseDto {
    private Long orderId;
    private Long paymentId;
    private String productName;
    private String productCode;
    private int quantity;
    private long price;
    private List<OrderOptionDataResponseDto> orderOptions;
}

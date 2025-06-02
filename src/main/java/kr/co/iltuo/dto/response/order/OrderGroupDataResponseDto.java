package kr.co.iltuo.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderGroupDataResponseDto {
    private Long paymentId;
    private Long userIdx;
    private Instant orderDate;
    private String orderStatusCode;
    private List<OrderDataResponseDto> orders;
}

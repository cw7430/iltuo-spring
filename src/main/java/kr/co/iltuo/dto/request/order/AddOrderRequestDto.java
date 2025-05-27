package kr.co.iltuo.dto.request.order;

import kr.co.iltuo.dto.request.IdxRequestDto;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
public class AddOrderRequestDto {
    private Long productId;
    private int quantity;
    private List<IdxRequestDto> options;
}

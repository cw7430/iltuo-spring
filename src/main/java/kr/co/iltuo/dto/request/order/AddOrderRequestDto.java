package kr.co.iltuo.dto.request.order;

import jakarta.validation.constraints.NotNull;
import kr.co.iltuo.dto.request.IdxRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddOrderRequestDto {
    @NotNull(message = "일련번호를 입력해주세요")
    private Long productId;
    private int quantity;
    private List<IdxRequestDto> options;
}

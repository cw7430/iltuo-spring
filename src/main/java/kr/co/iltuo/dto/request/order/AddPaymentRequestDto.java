package kr.co.iltuo.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.iltuo.common.validation.AllowedWords;
import kr.co.iltuo.dto.request.IdxRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddPaymentRequestDto {
    @NotNull(message = "일련번호를 입력해주세요")
    private Long paymentId;

    @NotBlank(message = "결제 방식 코드를 입력해주세요")
    @AllowedWords(value = {"PM001", "PM002"})
    private String paymentMethodCode;

    @NotBlank(message = "우편번호를 입력해주세요.")
    private String postalCode;

    @NotBlank(message = "주소를 입력해주세요.")
    private String defaultAddress;

    private String detailAddress;

    private String extraAddress;

    private List<IdxRequestDto> orderIds;
}

package kr.co.iltuo.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressRequestDto {
    @NotBlank(message = "우편번호를 입력해주세요.")
    private String postalCode;

    @NotBlank(message = "주소를 입력해주세요.")
    private String defaultAddress;

    private String detailAddress;

    private String extraAddress;

    private boolean main;
}

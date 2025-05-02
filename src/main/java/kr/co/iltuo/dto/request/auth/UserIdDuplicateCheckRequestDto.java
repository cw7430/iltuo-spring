package kr.co.iltuo.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
public class UserIdDuplicateCheckRequestDto {
    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,25}$",
            message = "아이디는 5자 이상 25자 이하, 영문 또는 영문+숫자의 조합이어야 합니다."
    )
    private String userId;
}

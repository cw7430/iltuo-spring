package kr.co.iltuo.common.response;

import kr.co.iltuo.common.code.ResponseCode;
import lombok.*;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T result;

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                result
        );
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return new ApiResponse<>(
                responseCode.getCode(),
                responseCode.getMessage(),
                null
        );
    }
}

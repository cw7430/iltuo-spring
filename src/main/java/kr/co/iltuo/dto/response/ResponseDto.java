package kr.co.iltuo.dto.response;

import kr.co.iltuo.common.code.ResponseCode;

public record ResponseDto<T>(String code, String message, T result) {
    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                result
        );
    }

    public static <T> ResponseDto<T> fail(ResponseCode responseCode) {
        return new ResponseDto<>(
                responseCode.getCode(),
                responseCode.getMessage(),
                null
        );
    }
}

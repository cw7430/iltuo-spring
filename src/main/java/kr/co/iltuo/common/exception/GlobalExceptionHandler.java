package kr.co.iltuo.common.exception;

import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 유효성 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                ApiResponse.fail(ResponseCode.VALIDATION_ERROR),
                ResponseCode.VALIDATION_ERROR.getStatus()
        );
    }

    // 커스텀 비즈니스 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(
                ApiResponse.fail(ex.getResponseCode()),
                ex.getResponseCode().getStatus()
        );
    }

    // 기타 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                ApiResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR),
                ResponseCode.INTERNAL_SERVER_ERROR.getStatus()
        );
    }
}


package com.example.restaurant.management.exception;

import com.example.restaurant.management.dto.ApiRe.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception){
//        ApiResponse apiResponse = new ApiResponse();
//
//        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
//        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiErrorResponse> handlingAppException(AppException exception, HttpServletRequest request){
        ErrorCode errorCode = exception.getErrorCode();
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .code(errorCode.httpStatus.value())
                .status(errorCode.httpStatus.toString())
                .message(errorCode.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().toString())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiErrorResponse);
    }
}


package ensharp.pinterest.global.exception.handler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "권한 없음. JWT 토큰 실어 보내던지 본인에게 권한이 없는거임"),
        @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
        @ApiResponse(responseCode = "400", description = "요청 형식 오류"),
        @ApiResponse(responseCode = "500", description = "서버 오류 -> 관리자한테 즉시!!! 문의 바람")
})
public @interface CommonErrorResponses {}


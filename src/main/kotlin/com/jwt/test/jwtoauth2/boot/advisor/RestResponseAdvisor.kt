package com.jwt.test.jwtoauth2.boot.advisor

import com.jwt.test.jwtoauth2.boot.dto.response.CommonResponseDto
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class RestResponseAdvisor<T>: ResponseBodyAdvice<T> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        val className: String = returnType.containingClass.simpleName
        if(className == "BasicErrorController" || className == "CustomExceptionHandler" || className == "OpenApiControllerWebMvc" || className == "ApiResourceController" ) {
            return false
        }

        return true
    }

    override fun beforeBodyWrite(
        body: T?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): T? {
        val httpResponse: ServletServerHttpResponse = response as ServletServerHttpResponse
        val status : HttpStatus = HttpStatus.valueOf(httpResponse.servletResponse.status)

        val responseDto = CommonResponseDto(
            success = true,
            status = status,
            message = "",
            data = body ?: "데이터가 없습니다.",
        )

        return responseDto as T
    }
}
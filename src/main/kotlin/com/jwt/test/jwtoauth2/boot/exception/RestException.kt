package com.jwt.test.jwtoauth2.boot.exception

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.ToString
import org.springframework.http.HttpStatus
import java.lang.RuntimeException

@Getter
@ToString
@NoArgsConstructor
class RestException(
    val httpStatus: HttpStatus,
    message: String
): RuntimeException(
    message
){
}
package com.albatros.springsecurity.controller

import com.albatros.springsecurity.domain.model.exception.AbstractApiException
import com.albatros.springsecurity.domain.model.exception.InvalidTokenException
import com.albatros.springsecurity.domain.model.exception.ValidationErrorException
import com.albatros.springsecurity.domain.model.response.ApiResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import io.micrometer.observation.annotation.Observed
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
@Observed(name = "ExceptionResolver")
class ExceptionResolver {

    @ExceptionHandler(value = [AbstractApiException::class])
    fun handle(cause: AbstractApiException, request: WebRequest): ResponseEntity<ApiResponse> {
        return cause.asResponse()
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handle(cause: ConstraintViolationException, request: WebRequest): ResponseEntity<ApiResponse> {
        return ValidationErrorException(
            cause.constraintViolations.map { v ->
                ValidationErrorException.Violation(v.propertyPath.toString(), v.message)
            }
        ).asResponse()
    }

    @ExceptionHandler(value = [SignatureException::class])
    fun handle(cause: SignatureException, request: WebRequest): ResponseEntity<ApiResponse> {
        return InvalidTokenException().asResponse()
    }

    @ExceptionHandler(value = [ExpiredJwtException::class])
    fun handle(cause: ExpiredJwtException, request: WebRequest): ResponseEntity<ApiResponse> {
        return InvalidTokenException().asResponse()
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handle(cause: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ApiResponse> {
        return ValidationErrorException(
            cause.bindingResult.fieldErrors.map { v ->
                ValidationErrorException.Violation(v.field, v.defaultMessage ?: "Unknown error")
            }
        ).asResponse()
    }
}

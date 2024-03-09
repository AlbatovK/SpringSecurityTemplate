package com.albatros.springsecurity.domain.model.exception

import org.springframework.http.HttpStatus

class ValidationErrorException(val violations: List<Violation>) : AbstractApiException() {
    override val message: String
        get() = "Validation error"
    override val status: HttpStatus
        get() = HttpStatus.BAD_REQUEST

    data class Violation(
        val fieldName: String,
        val message: String,
    )
}

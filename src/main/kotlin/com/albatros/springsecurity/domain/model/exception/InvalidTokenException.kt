package com.albatros.springsecurity.domain.model.exception

import org.springframework.http.HttpStatus

class InvalidTokenException : AbstractApiException() {
    override val status: HttpStatus
        get() = HttpStatus.FORBIDDEN
    override val message: String
        get() = "JWT Token is malformed or does not exist"
}

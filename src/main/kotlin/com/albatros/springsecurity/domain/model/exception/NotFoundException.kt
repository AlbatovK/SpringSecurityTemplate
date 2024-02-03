package com.albatros.springsecurity.domain.model.exception

import org.springframework.http.HttpStatus

class NotFoundException : AbstractApiException() {
    override val message: String
        get() = "Not found"
    override val status: HttpStatus
        get() = HttpStatus.NOT_FOUND
}

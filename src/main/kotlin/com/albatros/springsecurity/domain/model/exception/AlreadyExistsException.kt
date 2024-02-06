package com.albatros.springsecurity.domain.model.exception

import org.springframework.http.HttpStatus

class AlreadyExistsException(override val message: String) : AbstractApiException() {
    override val status: HttpStatus
        get() = HttpStatus.CONFLICT
}

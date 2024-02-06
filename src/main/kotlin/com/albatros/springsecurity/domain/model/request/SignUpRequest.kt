package com.albatros.springsecurity.domain.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequest(

    @field:Size(min = 5, max = 30)
    @field:NotBlank
    val username: String,

    @field:Email
    val email: String,

    @field:Size(min = 5, max = 30)
    @field:NotBlank
    val password: String

)
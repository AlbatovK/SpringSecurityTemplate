package com.albatros.springsecurity.domain.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignInRequest(

    @field:Size(min = 5, max = 30)
    @field:NotBlank
    val username: String,

    @field:Size(min = 5, max = 30)
    @field:NotBlank
    val password: String

)

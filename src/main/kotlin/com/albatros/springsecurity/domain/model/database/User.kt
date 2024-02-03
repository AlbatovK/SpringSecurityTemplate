package com.albatros.springsecurity.domain.model.database

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import java.io.Serializable

@Entity(name = "users")
@Validated
class User(
    @Column(nullable = false, length = 30)
    @field:NotBlank
    var name: String,
    @Column(nullable = false, length = 125)
    @field:Email
    var email: String,
) : AbstractEntity(), Serializable

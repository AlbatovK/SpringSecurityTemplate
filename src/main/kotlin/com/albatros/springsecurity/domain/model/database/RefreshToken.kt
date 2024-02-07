package com.albatros.springsecurity.domain.model.database

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import java.io.Serializable

@Entity
@Validated
class RefreshToken(

    @Column(nullable = false, length = 250)
    @field:NotBlank
    var value: String,

    @Column(nullable = false, length = 30)
    @field:NotBlank
    var ownerUsername: String

) : AbstractEntity(), Serializable

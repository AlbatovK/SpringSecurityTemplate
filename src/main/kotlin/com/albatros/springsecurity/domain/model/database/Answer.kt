package com.albatros.springsecurity.domain.model.database

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.validation.annotation.Validated
import java.io.Serializable

@Entity
@Validated
class Answer(
    var value: String,
    var correct: Boolean,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    var quiz: Quiz,
) : AbstractEntity(), Serializable

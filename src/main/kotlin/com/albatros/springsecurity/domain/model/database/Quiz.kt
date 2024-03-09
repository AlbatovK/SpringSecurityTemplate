package com.albatros.springsecurity.domain.model.database

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank
import java.io.Serializable
import org.springframework.validation.annotation.Validated

@Entity
@Validated
class Quiz(
    @Column(nullable = false, length = 250)
    @field:NotBlank
    var question: String,
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var type: QuizType = QuizType.SingleAnswer,
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var possibleAnswers: MutableList<Answer>,
) : AbstractEntity(), Serializable

enum class QuizType {
    SingleAnswer,
    MultipleAnswer,
    FreeAnswer,
}

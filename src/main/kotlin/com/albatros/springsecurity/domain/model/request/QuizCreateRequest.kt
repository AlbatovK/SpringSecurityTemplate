package com.albatros.springsecurity.domain.model.request

import com.albatros.springsecurity.domain.model.database.Answer
import com.albatros.springsecurity.domain.model.database.Quiz
import com.albatros.springsecurity.domain.model.database.QuizType
import java.time.LocalDateTime

data class QuizCreateRequest(
    val question: String,
    val type: QuizType,
    val possibleAnswers: List<AnswerDto>,
    val id: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

data class AnswerDto(
    val quizId: Long,
    val value: String,
    val correct: Boolean,
)

fun AnswerDto.toDomainObject(quiz: Quiz) =
    Answer(
        value = value,
        correct = correct,
        quiz = quiz,
    )

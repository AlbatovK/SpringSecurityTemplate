package com.albatros.springsecurity.domain.service

import com.albatros.springsecurity.domain.model.database.Quiz
import com.albatros.springsecurity.domain.model.request.QuizCreateRequest
import org.springframework.validation.annotation.Validated

@Validated
interface QuizService {
    fun getAll(): List<Quiz>

    fun getById(id: Long): Quiz

    fun deleteById(quizId: Long)

    fun save(quizCreateRequest: QuizCreateRequest)
}

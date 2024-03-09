package com.albatros.springsecurity.domain.service.impl

import com.albatros.springsecurity.domain.model.database.Quiz
import com.albatros.springsecurity.domain.model.exception.NotFoundException
import com.albatros.springsecurity.domain.model.request.QuizCreateRequest
import com.albatros.springsecurity.domain.model.request.toDomainObject
import com.albatros.springsecurity.domain.repository.QuizRepository
import com.albatros.springsecurity.domain.service.QuizService
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class QuizServiceImpl(
    private val repository: QuizRepository,
) : QuizService {
    override fun getAll(): List<Quiz> = repository.findAll()

    override fun getById(id: Long): Quiz = repository.findEntityById(id) ?: throw NotFoundException()

    override fun deleteById(quizId: Long) = repository.deleteById(quizId)

    override fun save(quizCreateRequest: QuizCreateRequest) {
        val quiz =
            Quiz(
                quizCreateRequest.question,
                quizCreateRequest.type,
                mutableListOf(),
            )
        quiz.possibleAnswers = quizCreateRequest.possibleAnswers.map { it.toDomainObject(quiz) }.toMutableList()

        repository.save(quiz)
    }
}

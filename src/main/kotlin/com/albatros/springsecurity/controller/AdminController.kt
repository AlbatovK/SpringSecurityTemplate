package com.albatros.springsecurity.controller

import com.albatros.springsecurity.domain.model.request.QuizCreateRequest
import com.albatros.springsecurity.domain.service.QuizService
import com.albatros.springsecurity.domain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("api/admin")
class AdminController(
    private val quizService: QuizService,
    private val userService: UserService,
) {
    @GetMapping("/users")
    @Operation(summary = "Get all users")
    fun getAllUsers() = userService.list()

    @GetMapping("/quiz")
    @Operation(summary = "Get all quizzes")
    fun getAllQuiz() = quizService.getAll()

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Get quiz by id")
    fun getQuizById(
        @PathVariable("quizId") quizId: Long,
    ) = quizService.getById(quizId)

    @DeleteMapping("/quiz/{quizId}")
    @Operation(summary = "Delete quiz by id")
    fun deleteQuizById(
        @PathVariable("quizId") quizId: Long,
    ) = quizService.deleteById(quizId)

    @PostMapping("/quiz", consumes = ["application/json"])
    @Operation(summary = "Save quiz")
    fun saveQuiz(
        @Valid @RequestBody quizCreateRequest: QuizCreateRequest,
    ) = quizService.save(quizCreateRequest)
}

package com.albatros.springsecurity.domain.repository

import com.albatros.springsecurity.domain.model.database.Quiz
import org.springframework.stereotype.Repository

@Repository
interface QuizRepository : CommonRepository<Quiz>

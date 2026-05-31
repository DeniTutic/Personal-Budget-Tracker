package com.example.mobilno_projekat.model.repository.auth

interface AuthRepository {

    suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    )

    suspend fun login(
        email: String,
        password: String
    ): Boolean
}
package com.example.mobilno_projekat.model.repository.auth

import com.example.mobilno_projekat.model.data.local.dao.UserDao
import com.example.mobilno_projekat.model.data.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        withContext(Dispatchers.IO) {
            val existingUser = userDao.getUserByEmail(email.trim())

            if (existingUser != null) {
                throw IllegalArgumentException("User with this email already exists.")
            }

            userDao.insertUser(
                UserEntity(
                    firstName = firstName.trim(),
                    lastName = lastName.trim(),
                    email = email.trim(),
                    password = password
                )
            )
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.login(
                email = email.trim(),
                password = password
            ) != null
        }
    }
}
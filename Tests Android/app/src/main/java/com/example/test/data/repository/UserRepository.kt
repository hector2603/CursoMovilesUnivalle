package com.example.test.data.repository

import com.example.test.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository {
    private val users = mutableListOf<User>()
    
    fun getUsers(): Flow<List<User>> = flow {
        delay(500) // Simulate network delay
        emit(users.toList())
    }
    
    suspend fun addUser(user: User): Result<User> {
        return try {
            delay(300) // Simulate network delay
            val newUser = user.copy(id = users.size + 1)
            users.add(newUser)
            Result.success(newUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserById(id: Int): User? {
        delay(200) // Simulate network delay
        return users.find { it.id == id }
    }
    
    suspend fun deleteUser(id: Int): Boolean {
        delay(200) // Simulate network delay
        return users.removeIf { it.id == id }
    }
}

package com.example.test.data.repository

import com.example.test.data.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    
    private lateinit var repository: UserRepository
    
    @Before
    fun setup() {
        repository = UserRepository()
    }
    
    @Test
    fun `getUsers returns empty list initially`() = runTest {
        val users = repository.getUsers().first()
        assertTrue(users.isEmpty())
    }
    
    @Test
    fun `addUser successfully adds user and returns success`() = runTest {
        val user = User(name = "John Doe", email = "john@example.com")
        
        val result = repository.addUser(user)
        
        assertTrue(result.isSuccess)
        val addedUser = result.getOrNull()
        assertNotNull(addedUser)
        assertEquals("John Doe", addedUser?.name)
        assertEquals("john@example.com", addedUser?.email)
        assertEquals(1, addedUser?.id)
    }
    
    @Test
    fun `addUser assigns incremental IDs`() = runTest {
        val user1 = User(name = "John", email = "john@example.com")
        val user2 = User(name = "Jane", email = "jane@example.com")
        
        val result1 = repository.addUser(user1)
        val result2 = repository.addUser(user2)
        
        assertEquals(1, result1.getOrNull()?.id)
        assertEquals(2, result2.getOrNull()?.id)
    }
    
    @Test
    fun `getUsers returns all added users`() = runTest {
        val user1 = User(name = "John", email = "john@example.com")
        val user2 = User(name = "Jane", email = "jane@example.com")
        
        repository.addUser(user1)
        repository.addUser(user2)
        
        val users = repository.getUsers().first()
        assertEquals(2, users.size)
        assertEquals("John", users[0].name)
        assertEquals("Jane", users[1].name)
    }
    
    @Test
    fun `getUserById returns correct user`() = runTest {
        val user = User(name = "John Doe", email = "john@example.com")
        val addedUser = repository.addUser(user).getOrNull()!!
        
        val foundUser = repository.getUserById(addedUser.id)
        
        assertNotNull(foundUser)
        assertEquals(addedUser.id, foundUser?.id)
        assertEquals("John Doe", foundUser?.name)
        assertEquals("john@example.com", foundUser?.email)
    }
    
    @Test
    fun `getUserById returns null for non-existent user`() = runTest {
        val foundUser = repository.getUserById(999)
        
        assertNull(foundUser)
    }
    
    @Test
    fun `deleteUser removes user and returns true`() = runTest {
        val user = User(name = "John Doe", email = "john@example.com")
        val addedUser = repository.addUser(user).getOrNull()!!
        
        val deleted = repository.deleteUser(addedUser.id)
        
        assertTrue(deleted)
        val users = repository.getUsers().first()
        assertTrue(users.isEmpty())
    }
    
    @Test
    fun `deleteUser returns false for non-existent user`() = runTest {
        val deleted = repository.deleteUser(999)
        
        assertFalse(deleted)
    }
    
    @Test
    fun `deleteUser only removes specified user`() = runTest {
        val user1 = User(name = "John", email = "john@example.com")
        val user2 = User(name = "Jane", email = "jane@example.com")
        
        val addedUser1 = repository.addUser(user1).getOrNull()!!
        val addedUser2 = repository.addUser(user2).getOrNull()!!
        
        repository.deleteUser(addedUser1.id)
        
        val users = repository.getUsers().first()
        assertEquals(1, users.size)
        assertEquals(addedUser2.id, users[0].id)
        assertEquals("Jane", users[0].name)
    }
}

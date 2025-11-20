package com.example.test.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.test.data.model.User
import com.example.test.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private val testDispatcher = StandardTestDispatcher()
    
    @Mock
    private lateinit var mockRepository: UserRepository
    
    private lateinit var viewModel: UserViewModel
    
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial state is correct`() = runTest {
        // Mock empty users list
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(emptyList()) })
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state.users.isEmpty())
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertFalse(state.isAddingUser)
    }
    
    @Test
    fun `loadUsers updates state with users from repository`() = runTest {
        val testUsers = listOf(
            User(1, "John", "john@example.com"),
            User(2, "Jane", "jane@example.com")
        )
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(testUsers) })
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertEquals(2, state.users.size)
        assertEquals("John", state.users[0].name)
        assertEquals("Jane", state.users[1].name)
        assertFalse(state.isLoading)
    }
    
    @Test
    fun `loadUsers shows loading state during operation`() = runTest {
        whenever(mockRepository.getUsers()).thenReturn(flow { 
            kotlinx.coroutines.delay(100)
            emit(emptyList()) 
        })
        
        viewModel = UserViewModel(mockRepository)
        
        // Advance a bit to let the loading state be set
        advanceTimeBy(50)
        
        // Should be loading
        assertTrue(viewModel.uiState.value.isLoading)
        
        // Complete the operation
        advanceUntilIdle()
        
        // After completion should not be loading
        assertFalse(viewModel.uiState.value.isLoading)
    }
    
    @Test
    fun `addUser with valid data calls repository and reloads users`() = runTest {
        val testUsers = listOf(User(1, "John", "john@example.com"))
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(testUsers) })
        whenever(mockRepository.addUser(any())).thenReturn(Result.success(User(1, "John", "john@example.com")))
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        viewModel.addUser("John", "john@example.com")
        advanceUntilIdle()
        
        verify(mockRepository).addUser(argThat { name == "John" && email == "john@example.com" })
        verify(mockRepository, times(2)).getUsers() // Once in init, once after adding
    }
    
    @Test
    fun `addUser with empty name shows error`() = runTest {
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(emptyList()) })
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        viewModel.addUser("", "john@example.com")
        
        val state = viewModel.uiState.value
        assertEquals("Name and email cannot be empty", state.errorMessage)
        verify(mockRepository, never()).addUser(any())
    }
    
    @Test
    fun `addUser with empty email shows error`() = runTest {
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(emptyList()) })
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        viewModel.addUser("John", "")
        
        val state = viewModel.uiState.value
        assertEquals("Name and email cannot be empty", state.errorMessage)
        verify(mockRepository, never()).addUser(any())
    }
    
    
    @Test
    fun `addUser failure shows error message`() = runTest {
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(emptyList()) })
        whenever(mockRepository.addUser(any())).thenReturn(Result.failure(Exception("Network error")))
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        viewModel.addUser("John", "john@example.com")
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertEquals("Failed to add user", state.errorMessage)
        assertFalse(state.isAddingUser)
    }
    
    @Test
    fun `deleteUser calls repository and reloads users`() = runTest {
        val testUsers = listOf(User(1, "John", "john@example.com"))
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(testUsers) })
        whenever(mockRepository.deleteUser(1)).thenReturn(true)
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        viewModel.deleteUser(1)
        advanceUntilIdle()
        
        verify(mockRepository).deleteUser(1)
        verify(mockRepository, times(2)).getUsers() // Once in init, once after deleting
    }
    
    @Test
    fun `deleteUser failure shows error message`() = runTest {
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(emptyList()) })
        whenever(mockRepository.deleteUser(1)).thenReturn(false)
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        viewModel.deleteUser(1)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertEquals("Failed to delete user", state.errorMessage)
    }
    
    @Test
    fun `clearError removes error message`() = runTest {
        whenever(mockRepository.getUsers()).thenReturn(flow { emit(emptyList()) })
        
        viewModel = UserViewModel(mockRepository)
        advanceUntilIdle()
        
        // Set an error
        viewModel.addUser("", "")
        assertEquals("Name and email cannot be empty", viewModel.uiState.value.errorMessage)
        
        // Clear the error
        viewModel.clearError()
        assertNull(viewModel.uiState.value.errorMessage)
    }
}

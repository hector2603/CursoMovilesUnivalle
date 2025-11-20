package com.example.test.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel()
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial counter value is zero`() {
        assertEquals(0, viewModel.counter.value)
    }
    
    @Test
    fun `initial loading state is false`() {
        assertFalse(viewModel.isLoading.value)
    }
    
    @Test
    fun `incrementCounter increases counter by one`() {
        viewModel.incrementCounter()
        assertEquals(1, viewModel.counter.value)
        
        viewModel.incrementCounter()
        assertEquals(2, viewModel.counter.value)
    }
    
    @Test
    fun `decrementCounter decreases counter by one`() {
        viewModel.incrementCounter()
        viewModel.incrementCounter()
        assertEquals(2, viewModel.counter.value)
        
        viewModel.decrementCounter()
        assertEquals(1, viewModel.counter.value)
        
        viewModel.decrementCounter()
        assertEquals(0, viewModel.counter.value)
    }
    
    @Test
    fun `decrementCounter can go below zero`() {
        viewModel.decrementCounter()
        assertEquals(-1, viewModel.counter.value)
    }
    
    @Test
    fun `resetCounter sets counter to zero`() {
        viewModel.incrementCounter()
        viewModel.incrementCounter()
        viewModel.incrementCounter()
        assertEquals(3, viewModel.counter.value)
        
        viewModel.resetCounter()
        assertEquals(0, viewModel.counter.value)
    }
    
    @Test
    fun `resetCounter works when counter is negative`() {
        viewModel.decrementCounter()
        viewModel.decrementCounter()
        assertEquals(-2, viewModel.counter.value)
        
        viewModel.resetCounter()
        assertEquals(0, viewModel.counter.value)
    }
    
    @Test
    fun `simulateLoading sets loading state correctly`() = runTest {
        assertFalse(viewModel.isLoading.value)
        
        viewModel.simulateLoading()
        
        // Advance a bit to let the loading state be set
        advanceTimeBy(100)
        
        // Should be loading
        assertTrue(viewModel.isLoading.value)
        
        // Advance time to complete the loading
        advanceTimeBy(2000)
        
        // Should not be loading anymore
        assertFalse(viewModel.isLoading.value)
    }
    
    @Test
    fun `multiple counter operations work correctly`() {
        viewModel.incrementCounter()
        viewModel.incrementCounter()
        viewModel.decrementCounter()
        viewModel.incrementCounter()
        
        assertEquals(2, viewModel.counter.value)
        
        viewModel.resetCounter()
        assertEquals(0, viewModel.counter.value)
    }
}

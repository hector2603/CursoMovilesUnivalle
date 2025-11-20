package com.example.test.presentation.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test.presentation.viewmodel.HomeViewModel
import com.example.test.ui.theme.TestTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun homeScreen_displaysWelcomeMessage() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithText("Welcome to Test App")
            .assertIsDisplayed()
    }
    
    @Test
    fun homeScreen_displaysInitialCounterValue() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithText("0")
            .assertIsDisplayed()
    }
    
    @Test
    fun homeScreen_incrementButtonIncreasesCounter() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        // Click increment button
        composeTestRule
            .onNodeWithText("+")
            .performClick()
        
        // Verify counter increased
        composeTestRule
            .onNodeWithText("1")
            .assertIsDisplayed()
    }
    
    @Test
    fun homeScreen_decrementButtonDecreasesCounter() {
        val viewModel = HomeViewModel()
        
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = viewModel
                )
            }
        }
        
        // First increment to have a positive value
        composeTestRule
            .onNodeWithText("+")
            .performClick()
        
        // Then decrement
        composeTestRule
            .onNodeWithText("-")
            .performClick()
        
        // Verify counter is back to 0
        composeTestRule
            .onNodeWithText("0")
            .assertIsDisplayed()
    }
    
    @Test
    fun homeScreen_resetButtonResetsCounter() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        // Increment counter multiple times
        composeTestRule
            .onNodeWithText("+")
            .performClick()
        composeTestRule
            .onNodeWithText("+")
            .performClick()
        composeTestRule
            .onNodeWithText("+")
            .performClick()
        
        // Verify counter is 3
        composeTestRule
            .onNodeWithText("3")
            .assertIsDisplayed()
        
        // Click reset
        composeTestRule
            .onNodeWithText("Reset")
            .performClick()
        
        // Verify counter is back to 0
        composeTestRule
            .onNodeWithText("0")
            .assertIsDisplayed()
    }
    
    @Test
    fun homeScreen_simulateLoadingButtonShowsLoadingState() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        // Click simulate loading button
        composeTestRule
            .onNodeWithText("Simulate Loading")
            .performClick()
        
        // Verify loading state is shown
        composeTestRule
            .onNodeWithText("Loading...")
            .assertIsDisplayed()
        
        // Verify buttons are disabled during loading
        composeTestRule
            .onNodeWithText("+")
            .assertIsNotEnabled()
        
        composeTestRule
            .onNodeWithText("-")
            .assertIsNotEnabled()
        
        composeTestRule
            .onNodeWithText("Reset")
            .assertIsNotEnabled()
    }
    
    @Test
    fun homeScreen_goToUsersButtonIsDisplayed() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithText("Go to Users")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
    
    @Test
    fun homeScreen_counterCardIsDisplayed() {
        composeTestRule.setContent {
            TestTheme {
                HomeScreen(
                    onNavigateToUsers = {},
                    viewModel = HomeViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithText("Counter")
            .assertIsDisplayed()
    }
}

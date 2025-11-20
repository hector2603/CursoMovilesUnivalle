package com.example.test.presentation.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test.presentation.viewmodel.UserViewModel
import com.example.test.ui.theme.TestTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun usersScreen_displaysTitle() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithText("Users")
            .assertIsDisplayed()
    }
    
    @Test
    fun usersScreen_displaysBackButton() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
    
    @Test
    fun usersScreen_displaysAddButton() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithContentDescription("Add User")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
    
    @Test
    fun usersScreen_displaysEmptyStateWhenNoUsers() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        composeTestRule
            .onNodeWithText("No users yet. Add some users!")
            .assertIsDisplayed()
    }
    
    @Test
    fun usersScreen_addButtonOpensDialog() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        // Click add button
        composeTestRule
            .onNodeWithContentDescription("Add User")
            .performClick()
        
        // Verify dialog is shown
        composeTestRule
            .onNodeWithText("Add New User")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("Name")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("Email")
            .assertIsDisplayed()
    }
    
    @Test
    fun addUserDialog_hasRequiredFields() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        // Open dialog
        composeTestRule
            .onNodeWithContentDescription("Add User")
            .performClick()
        
        // Verify text fields are present
        composeTestRule
            .onAllNodesWithText("Name")
            .onFirst()
            .assertIsDisplayed()
        
        composeTestRule
            .onAllNodesWithText("Email")
            .onFirst()
            .assertIsDisplayed()
        
        // Verify buttons are present
        composeTestRule
            .onNodeWithText("Add")
            .assertIsDisplayed()
        
        composeTestRule
            .onNodeWithText("Cancel")
            .assertIsDisplayed()
    }
    
    @Test
    fun addUserDialog_cancelButtonClosesDialog() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        // Open dialog
        composeTestRule
            .onNodeWithContentDescription("Add User")
            .performClick()
        
        // Verify dialog is open
        composeTestRule
            .onNodeWithText("Add New User")
            .assertIsDisplayed()
        
        // Click cancel
        composeTestRule
            .onNodeWithText("Cancel")
            .performClick()
        
        // Verify dialog is closed
        composeTestRule
            .onNodeWithText("Add New User")
            .assertDoesNotExist()
    }
    
    @Test
    fun addUserDialog_addButtonIsDisabledWhenFieldsEmpty() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        // Open dialog
        composeTestRule
            .onNodeWithContentDescription("Add User")
            .performClick()
        
        // Add button should be disabled when fields are empty
        composeTestRule
            .onNodeWithText("Add")
            .assertIsNotEnabled()
    }
    
    @Test
    fun addUserDialog_canEnterText() {
        composeTestRule.setContent {
            TestTheme {
                UsersScreen(
                    onNavigateBack = {},
                    onNavigateToProfile = {},
                    viewModel = UserViewModel()
                )
            }
        }
        
        // Open dialog
        composeTestRule
            .onNodeWithContentDescription("Add User")
            .performClick()
        
        // Enter text in name field
        composeTestRule
            .onAllNodesWithText("Name")
            .onLast() // Get the text field, not the label
            .performTextInput("John Doe")
        
        // Enter text in email field
        composeTestRule
            .onAllNodesWithText("Email")
            .onLast() // Get the text field, not the label
            .performTextInput("john@example.com")
        
        // Add button should now be enabled
        composeTestRule
            .onNodeWithText("Add")
            .assertIsEnabled()
    }
}

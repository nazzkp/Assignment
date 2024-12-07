package com.example.assignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assignment.view.HomeScreen
import com.example.assignment.view.LoginScreen
import com.example.assignment.view.navigation.AppNavigation
import org.junit.Rule
import org.junit.Test

class TestingClass {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigation() {

        lateinit var navController: NavHostController
        composeTestRule.setContent {
            navController = rememberNavController()
            AppNavigation()
        }


        composeTestRule
            .onNodeWithText("Username")
            .performTextInput("JohnDoe")


        composeTestRule
            .onNodeWithText("Password")
            .performTextInput("1234")


        composeTestRule
            .onNodeWithText("Login")
            .performClick()


        assert(navController.currentBackStackEntry?.destination?.route == "Home?JohnDoe")


        composeTestRule
            .onNodeWithText("Hi JohnDoe")
            .assertIsDisplayed()
    }


    @Test
    fun testButton() {
        val wasClicked = false
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController)
        }

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()

        composeTestRule.onNodeWithText("Login").assertIsEnabled()

        composeTestRule.onNodeWithText("Login").performClick()

        assert(wasClicked)
    }

    @Test
    fun testProgressbar() {
        // Arrange: Set the content with loading state
        composeTestRule.setContent {
            HomeScreen("Test")
        }

        // Act: Simulate loading state change
        // Since the state is internal, we can't directly manipulate it in the test.
        // You might need to refactor your composable to allow state manipulation for testing.

        // Assert: Check if the progress bar is displayed
        composeTestRule.onNodeWithTag("Progress Bar").assertIsDisplayed()
    }
}
package com.example.randomuser.presentation

import com.example.randomuser.domain.ApiResponse
import com.example.randomuser.domain.User
import com.example.randomuser.domain.UserRepository
import com.example.randomuser.util.TestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RandomUserViewModelTest {

    private lateinit var viewModel: RandomUserViewModel
    private val userRepository: UserRepository = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RandomUserViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `requestRandomUser success, should update requestedUser and selectedUser`() = runTest {
        // Arrange
        val fakeUser = TestData.fakeUser
        val successResponse = ApiResponse.Success(fakeUser)
        whenever(userRepository.requestRandomUser("female", "US")).thenReturn(successResponse)
        whenever(userRepository.getUsers()).thenReturn(flowOf(emptyList()))

        // Act
        viewModel.requestRandomUser("female", "US")

        // Assert
        assertTrue(viewModel.requestedUser is ApiResponse.Loading)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(successResponse, viewModel.requestedUser)
        assertEquals(fakeUser, viewModel.selectedUser.first())
        verify(userRepository).insertUser(fakeUser)
    }

    @Test
    fun `requestRandomUser error, should update requestedUser`() = runTest {
        // Arrange
        val errorResponse = ApiResponse.Error<User>("Network error")
        whenever(userRepository.requestRandomUser("male", "GB")).thenReturn(errorResponse)
        whenever(userRepository.getUsers()).thenReturn(flowOf(emptyList()))

        // Act
        viewModel.requestRandomUser("male", "GB")

        // Assert
        assertTrue(viewModel.requestedUser is ApiResponse.Loading)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(errorResponse, viewModel.requestedUser)
    }

    @Test
    fun `onUserSelected, should update selectedUser`() = runTest {
        // Arrange
        val fakeUser = TestData.fakeUser

        // Act
        viewModel.onUserSelected(fakeUser)

        // Assert
        assertEquals(fakeUser, viewModel.selectedUser.first())
    }
}

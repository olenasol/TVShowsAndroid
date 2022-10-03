package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.AuthResult
import com.kotlin.olena.tvshowsapp.domain.models.AuthState
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RegisterNewUserUseCaseTest {
    @MockK
    private lateinit var repository: LoginRepository

    private lateinit var useCase: RegisterNewUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = RegisterNewUserUseCaseImpl(repository)
    }

    @Test
    fun `When login is successful, then success auth state emitted`() = runTest {
        //Arrange
        val email = "olena@gmail.com"
        val password = "123456"
        coEvery { repository.register(email, password) } returns AuthResult.Success
        //Act
        val authStates = mutableListOf<AuthState>()
        useCase.invoke(email, password).toList(authStates)
        //Assert
        authStates shouldBe listOf(AuthState.Loading, AuthState.Success)

    }

    @Test
    fun `When login has failed, then error auth state emitted`() = runTest {
        //Arrange
        val email = "olena@gmail.com"
        val password = "123456"
        val errorMessage = "Something went wrong"
        coEvery { repository.register(email, password) } returns AuthResult.Error(errorMessage)
        //Act
        val authStates = mutableListOf<AuthState>()
        useCase.invoke(email, password).toList(authStates)
        //Assert
        authStates[0] shouldBe AuthState.Loading
        authStates[1] shouldBeEqualToComparingFields AuthState.Error(errorMessage)

    }
}
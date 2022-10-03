package com.kotlin.olena.tvshowsapp.presentation.prelogin.registration

import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.domain.models.FieldInputState
import com.kotlin.olena.tvshowsapp.domain.models.AuthState
import com.kotlin.olena.tvshowsapp.domain.models.InputField
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper
import com.kotlin.olena.tvshowsapp.other.MIN_PASSWORD_LENGTH
import com.kotlin.olena.tvshowsapp.utils.MainCoroutineRule
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RegistrationViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RegistrationViewModel

    @MockK
    private lateinit var registerNewUserUseCase: RegisterNewUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = RegistrationViewModel(registerNewUserUseCase)
    }

    @Test
    fun `When view model is initialized, then initial input field state is valid `() = runTest {
        //Act
        val inputState = viewModel.inputFieldsState.first()
        //Assert
        inputState shouldBe mapOf<InputField, FieldInputState>(
            InputField.EMAIL to FieldInputState.Valid,
            InputField.PASSWORD to FieldInputState.Valid,
            InputField.CONFIRM_PASSWORD to FieldInputState.Valid,
        )
    }

    @Test
    fun `When password is too short, then input field state contains incorrect email`() = runTest {
        runFieldInputTest(
            passwordState = FieldInputState.Invalid(
                R.string.password_too_short,
                MIN_PASSWORD_LENGTH
            )
        )
    }

    @Test
    fun `When email has incorrect format, then input field state contains incorrect email`() =
        runTest {
            runFieldInputTest(emailState = FieldInputState.Invalid(R.string.incorrect_email_format))
        }

    @Test
    fun `When paswords are mismatched, then input field state contains incorrect email`() =
        runTest {
            runFieldInputTest(confirmPasswordState = FieldInputState.Invalid(R.string.passwords_do_not_match))
        }

    @Test
    fun `When not all fields are valid, then use case not called`() =
        runTest {
            runFieldInputTest(confirmPasswordState = FieldInputState.Invalid(R.string.passwords_do_not_match))
            verify(exactly = 0) {
                registerNewUserUseCase.invoke(any(), any())
            }
        }

    @Test
    fun `When all fields are valid, then use case is called`() = runTest {
        //Arrange
        val authState = AuthState.Success
        val email = "test@gmail.com"
        val password = "pass"
        every { registerNewUserUseCase.invoke(email, password) } returns flowOf(authState)
        mockkObject(InputValidationHelper)
        every { InputValidationHelper.getEmailState(any()) } returns FieldInputState.Valid
        every { InputValidationHelper.getPasswordState(any()) } returns FieldInputState.Valid
        every { InputValidationHelper.getConfirmPasswordState(any(), any()) } returns FieldInputState.Valid
        every { InputValidationHelper.areAllFieldsValid(any()) } returns true
        //Act
        val result = mutableListOf<AuthState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.registerState.toList(result)
        }
        viewModel.registerUser(email, password, password)
        //Assert
        verify(exactly = 1) {
            registerNewUserUseCase.invoke(email, password)
        }
        result[0] shouldBe authState
        collectJob.cancel()
    }


    private fun TestScope.runFieldInputTest(
        emailState: FieldInputState = FieldInputState.Valid,
        passwordState: FieldInputState = FieldInputState.Valid,
        confirmPasswordState: FieldInputState = FieldInputState.Valid,
        email: String = "olena.s",
        password: String = "test"
    ) {
        //Arrange
        mockkObject(InputValidationHelper)
        every { InputValidationHelper.getEmailState(any()) } returns emailState
        every { InputValidationHelper.getPasswordState(any()) } returns passwordState
        every {
            InputValidationHelper.getConfirmPasswordState(
                any(),
                any()
            )
        } returns confirmPasswordState
        //Act
        val result = mutableListOf<Map<InputField, FieldInputState>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.inputFieldsState.toList(result)
        }
        viewModel.registerUser(email, password, password)
        //Assert
        result[1] shouldBe mapOf(
            InputField.EMAIL to emailState,
            InputField.PASSWORD to passwordState,
            InputField.CONFIRM_PASSWORD to confirmPasswordState,
        )
        collectJob.cancel()
    }
}
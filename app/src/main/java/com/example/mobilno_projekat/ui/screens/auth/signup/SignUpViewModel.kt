package com.example.mobilno_projekat.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.model.repository.auth.AuthRepository
import com.example.mobilno_projekat.ui.screens.auth.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Init)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                when {
                    firstName.isBlank() -> {
                        _uiState.value = AuthUiState.Error("First name cannot be empty.")
                    }

                    lastName.isBlank() -> {
                        _uiState.value = AuthUiState.Error("Last name cannot be empty.")
                    }

                    email.isBlank() -> {
                        _uiState.value = AuthUiState.Error("Email cannot be empty.")
                    }

                    !email.contains("@") -> {
                        _uiState.value = AuthUiState.Error("Email must be valid.")
                    }

                    password.length < 4 -> {
                        _uiState.value = AuthUiState.Error("Password must have at least 4 characters.")
                    }

                    else -> {
                        authRepository.signUp(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            password = password
                        )
                        _uiState.value = AuthUiState.Success
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalArgumentException) {
                _uiState.value = AuthUiState.Error(e.message ?: "Sign up failed.")
            }
        }
    }
}
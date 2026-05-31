package com.example.mobilno_projekat.ui.screens.auth.login

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Init)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                when {
                    email.isBlank() -> {
                        _uiState.value = AuthUiState.Error("Email cannot be empty.")
                    }

                    password.isBlank() -> {
                        _uiState.value = AuthUiState.Error("Password cannot be empty.")
                    }

                    else -> {
                        val success = authRepository.login(email, password)

                        if (success) {
                            _uiState.value = AuthUiState.Success
                        } else {
                            _uiState.value = AuthUiState.Error("Invalid email or password.")
                        }
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalArgumentException) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed.")
            }
        }
    }
}
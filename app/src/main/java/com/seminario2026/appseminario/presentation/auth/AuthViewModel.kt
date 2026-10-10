package com.seminario2026.appseminario.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar la lógica de negocio y el estado del flujo de autenticación
 * (Login, Registro y Recuperación de Contraseña) para la app "AppSeminario".
 */
class AuthViewModel : ViewModel() {

    // MutableStateFlow interno para modificar el estado de forma segura dentro del ViewModel
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)

    // StateFlow público e inmutable expuesto a la UI mediante asStateFlow()
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    companion object {
        // Expresión regular estándar para validación básica de correo electrónico
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        private const val MIN_PASSWORD_LENGTH = 6
        private const val MIN_NAME_LENGTH = 2
    }

    /**
     * Resetea el estado actual a [AuthUiState.Idle].
     */
    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }

    /**
     * Realiza el proceso de inicio de sesión validando credenciales.
     */
    fun login(email: String, password: String) {
        val validationError = validateEmailAndPassword(email, password)
        if (validationError != null) {
            _uiState.value = AuthUiState.Error(validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Simulación de llamada a repositorio / caso de uso de autenticación
                delay(1500)
                
                // Simulación de éxito
                _uiState.value = AuthUiState.Success("¡Bienvenido de nuevo a AppSeminario, $email!")
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Error desconocido en el inicio de sesión.")
            }
        }
    }

    /**
     * Realiza el proceso de registro de un nuevo usuario.
     */
    fun register(name: String, email: String, password: String) {
        val trimmedName = name.trim()
        if (trimmedName.length < MIN_NAME_LENGTH) {
            _uiState.value = AuthUiState.Error("El nombre debe tener al menos $MIN_NAME_LENGTH caracteres.")
            return
        }

        val validationError = validateEmailAndPassword(email, password)
        if (validationError != null) {
            _uiState.value = AuthUiState.Error(validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Simulación de llamada a repositorio / caso de uso de registro
                delay(1500)

                // Simulación de éxito
                _uiState.value = AuthUiState.Success("¡Cuenta creada exitosamente para $trimmedName!")
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Error desconocido en el registro.")
            }
        }
    }

    /**
     * Realiza el proceso de recuperación de contraseña enviando un correo electrónico.
     */
    fun resetPassword(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _uiState.value = AuthUiState.Error("El correo electrónico no puede estar vacío.")
            return
        }
        if (!EMAIL_REGEX.matches(trimmedEmail)) {
            _uiState.value = AuthUiState.Error("El formato del correo electrónico no es válido.")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Simulación de llamada a repositorio / caso de uso de recuperación
                delay(1500)

                // Simulación de éxito
                _uiState.value = AuthUiState.Success("Correo de recuperación enviado a $trimmedEmail.")
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Error al enviar el correo de recuperación.")
            }
        }
    }

    /**
     * Valida de forma común el correo electrónico y la contraseña.
     * Retorna un mensaje de error si alguna validación falla, o null si es correcta.
     */
    private fun validateEmailAndPassword(email: String, password: String): String? {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            return "El correo electrónico no puede estar vacío."
        }
        if (!EMAIL_REGEX.matches(trimmedEmail)) {
            return "El formato del correo electrónico no es válido."
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return "La contraseña debe tener al menos $MIN_PASSWORD_LENGTH caracteres."
        }
        return null
    }
}

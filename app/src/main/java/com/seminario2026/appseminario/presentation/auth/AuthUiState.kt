package com.seminario2026.appseminario.presentation.auth

/**
 * Representa los diferentes estados de la interfaz de usuario para el flujo de autenticación
 * (Login, Registro y Recuperación de Contraseña) siguiendo Clean Architecture.
 */
sealed interface AuthUiState {
    /** Estado inicial o inactivo antes de que el usuario realice una acción. */
    object Idle : AuthUiState

    /** Estado de carga mientras se procesa la petición de autenticación. */
    object Loading : AuthUiState

    /** Estado de éxito cuando la autenticación o recuperación se completa correctamente. */
    data class Success(val message: String) : AuthUiState

    /** Estado de error cuando ocurre un fallo en la validación o en el proceso de autenticación. */
    data class Error(val errorMessage: String) : AuthUiState
}

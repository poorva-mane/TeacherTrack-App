package com.example.student_attendance.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun login(email: String, pass: String): Boolean {
        if (email.isNotBlank() && pass.isNotBlank()) {
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun signup(firstName: String, lastName: String, mobile: String, email: String, pass: String): Boolean {
        if (email.isNotBlank() && pass.isNotBlank() && firstName.isNotBlank()) {
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}
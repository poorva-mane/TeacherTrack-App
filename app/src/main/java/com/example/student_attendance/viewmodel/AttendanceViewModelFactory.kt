package com.example.student_attendance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.student_attendance.repository.AttendanceRepository

class AttendanceViewModelFactory(
    private val repository: AttendanceRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AttendanceViewModel::class.java) -> {
                AttendanceViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
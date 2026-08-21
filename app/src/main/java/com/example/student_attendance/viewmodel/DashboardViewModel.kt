package com.example.student_attendance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.repository.AttendanceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(repository: AttendanceRepository) : ViewModel() {

    val students: StateFlow<List<Student>> = repository.allStudents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalStudents: Int get() = students.value.size
    val presentToday: Int get() = students.value.count { it.isPresent }
    val attendanceRate: Int
        get() = if (totalStudents > 0) (presentToday * 100) / totalStudents else 0
}
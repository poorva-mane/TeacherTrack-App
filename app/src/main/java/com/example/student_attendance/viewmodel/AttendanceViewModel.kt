package com.example.student_attendance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.repository.AttendanceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AttendanceViewModel(private val repository: AttendanceRepository) : ViewModel() {

    val students: StateFlow<List<Student>> = repository.allStudents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addStudent(name: String, rollNo: String, division: String = "CSE SE-A") {
        viewModelScope.launch {
            repository.insertStudent(
                Student(
                    name = name,
                    rollNo = rollNo,
                    division = division,
                    isPresent = true
                )
            )
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            repository.deleteStudent(student)
        }
    }

    fun toggleAttendance(studentId: Long, isPresent: Boolean) {
        viewModelScope.launch {
            repository.updateAttendanceStatus(studentId, isPresent)
        }
    }
}
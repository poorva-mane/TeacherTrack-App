package com.example.student_attendance



import android.app.Application
import com.example.student_attendance.data.local.AppDatabase
import com.example.student_attendance.repository.AttendanceRepository

class AttendanceApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        AttendanceRepository(
            studentDao = database.studentDao(),
            attendanceDao = database.attendanceDao(),
            userDao = database.userDao()
        )
    }
}
package com.example.student_attendance.repository

import com.example.student_attendance.data.local.dao.AttendanceDao
import com.example.student_attendance.data.local.dao.StudentDao
import com.example.student_attendance.data.local.dao.UserDao
import com.example.student_attendance.data.local.entity.Attendance
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.data.local.entity.User
import kotlinx.coroutines.flow.Flow

class AttendanceRepository(
    private val studentDao: StudentDao,
    private val attendanceDao: AttendanceDao,
    private val userDao: UserDao
) {
    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()

    suspend fun insertStudent(student: Student): Long = studentDao.insertStudent(student)
    suspend fun updateStudent(student: Student) = studentDao.updateStudent(student)
    suspend fun deleteStudent(student: Student) = studentDao.deleteStudent(student)
    suspend fun updateAttendanceStatus(studentId: Long, isPresent: Boolean) =
        studentDao.updateAttendanceStatus(studentId, isPresent)

    suspend fun insertUser(user: User): Long = userDao.insertUser(user)
    suspend fun login(email: String, pass: String): User? = userDao.login(email, pass)

    suspend fun insertAttendanceRecord(attendance: Attendance) = attendanceDao.insertAttendance(attendance)
    fun getAttendanceByDate(date: String): Flow<List<Attendance>> = attendanceDao.getAttendanceByDate(date)
    fun getPresentCount(date: String): Flow<Int> = attendanceDao.getPresentCount(date)
}
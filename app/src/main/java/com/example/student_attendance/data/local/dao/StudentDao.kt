package com.example.student_attendance.data.local.dao

import androidx.room.*
import com.example.student_attendance.data.local.entity.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY rollNo ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("UPDATE students SET isPresent = :isPresent WHERE id = :studentId")
    suspend fun updateAttendanceStatus(studentId: Long, isPresent: Boolean)
}
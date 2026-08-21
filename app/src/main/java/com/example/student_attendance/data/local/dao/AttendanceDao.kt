package com.example.student_attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.student_attendance.data.local.entity.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): Flow<List<Attendance>>

    @Query("SELECT COUNT(*) FROM attendance WHERE isPresent = 1 AND date = :date")
    fun getPresentCount(date: String): Flow<Int>
}
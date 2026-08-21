package com.example.student_attendance.data.local.entity




import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val mobile: String = "",
    val email: String = "",
    val passwordHash: String = ""
)
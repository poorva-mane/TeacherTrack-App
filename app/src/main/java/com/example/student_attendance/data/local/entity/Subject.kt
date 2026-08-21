package com.example.student_attendance.data.local.entity





import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectName: String = "",
    val roomOrLab: String = "",
    val timing: String = "",
    val section: String = ""
)
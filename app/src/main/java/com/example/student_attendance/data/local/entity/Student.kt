package com.example.student_attendance.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "rollNo")
    val rollNo: String = "",
    @ColumnInfo(name = "department")
    val department: String = "CSE",
    @ColumnInfo(name = "year")
    val year: String = "Second Year",
    @ColumnInfo(name = "division")
    val division: String = "SE-A",
    @ColumnInfo(name = "isPresent")
    val isPresent: Boolean = true
)
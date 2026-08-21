package com.example.student_attendance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.student_attendance.data.local.dao.AttendanceDao
import com.example.student_attendance.data.local.dao.StudentDao
import com.example.student_attendance.data.local.dao.SubjectDao
import com.example.student_attendance.data.local.dao.UserDao
import com.example.student_attendance.data.local.entity.Attendance
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.data.local.entity.Subject
import com.example.student_attendance.data.local.entity.User

@Database(
    entities = [
        User::class,
        Student::class,
        Subject::class,
        Attendance::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
    abstract fun subjectDao(): SubjectDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "teacher_track_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
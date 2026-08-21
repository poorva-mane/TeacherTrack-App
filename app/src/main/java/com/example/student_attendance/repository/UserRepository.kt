package com.example.student_attendance.repository





import androidx.compose.runtime.mutableStateListOf

data class StudentItem(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val rollNo: String,
    val department: String = "CSE",
    val year: String = "Second Year",
    val division: String = "SE-A",
    var isPresentToday: Boolean = true
)

object UserRepository {
    val studentList = mutableStateListOf(
        StudentItem(id = 1, name = "Poorva Mane", rollNo = "101", division = "CSE SE-A", isPresentToday = true),
        StudentItem(id = 2, name = "Poonam Patil", rollNo = "102", division = "CSE SE-A", isPresentToday = false),
        StudentItem(id = 3, name = "Rahul Patil", rollNo = "103", division = "CSE SE-A", isPresentToday = true),
        StudentItem(id = 4, name = "Snehal Deshmukh", rollNo = "104", division = "CSE SE-A", isPresentToday = false)
    )

    fun addStudent(name: String, rollNo: String, division: String) {
        studentList.add(StudentItem(name = name, rollNo = rollNo, division = division, isPresentToday = true))
    }

    fun updateStudent(student: StudentItem) {
        val index = studentList.indexOfFirst { it.id == student.id }
        if (index != -1) studentList[index] = student
    }

    fun deleteStudent(student: StudentItem) {
        studentList.remove(student)
    }

    fun toggleAttendance(studentId: Long, isPresent: Boolean) {
        val index = studentList.indexOfFirst { it.id == studentId }
        if (index != -1) {
            studentList[index] = studentList[index].copy(isPresentToday = isPresent)
        }
    }
}
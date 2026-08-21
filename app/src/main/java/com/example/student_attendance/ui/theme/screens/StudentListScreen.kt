package com.example.student_attendance.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_attendance.R
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.ui.theme.*
import com.example.student_attendance.viewmodel.AttendanceViewModel

@Composable
fun StudentListScreen(viewModel: AttendanceViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingStudent by remember { mutableStateOf<Student?>(null) }

    val studentList: List<Student> by viewModel.students.collectAsState(initial = emptyList())

    val filteredStudents = studentList.filter { student ->
        student.name.contains(searchQuery, ignoreCase = true) || student.rollNo.contains(searchQuery)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = PrimaryBlue,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_groups_24),
                    contentDescription = "Add Student",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightGreyBg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavyDark)
                    .padding(20.dp)
            ) {
                Text("Students", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Manage your students and classes", color = TextMuted, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by name or roll number...", color = TextMuted, fontSize = 12.sp) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = NavySurface,
                        focusedContainerColor = NavySurface,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("CSE • Second Year • Division A", color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Student List", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Surface(shape = RoundedCornerShape(6.dp), color = InputBg) {
                    Text(
                        "${filteredStudents.size} ACTIVE",
                        fontSize = 10.sp,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredStudents, key = { student -> student.id }) { student ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(InputBg, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (student.name.isNotBlank()) student.name.take(1).uppercase() else "S",
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(student.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Roll No: ${student.rollNo} • ${student.division}", fontSize = 11.sp, color = TextMuted)
                                }
                            }
                            Row {
                                TextButton(onClick = { editingStudent = student }) {
                                    Text("Edit", color = PrimaryBlue, fontSize = 12.sp)
                                }
                                TextButton(onClick = { viewModel.deleteStudent(student) }) {
                                    Text("Delete", color = AccentRed, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        StudentFormDialog(
            title = "Add Student",
            onDismiss = { showAddDialog = false },
            onConfirm = { name, roll, div ->
                viewModel.addStudent(name, roll, div)
                showAddDialog = false
            }
        )
    }

    editingStudent?.let { student ->
        StudentFormDialog(
            title = "Edit Student",
            initialName = student.name,
            initialRoll = student.rollNo,
            initialDiv = student.division,
            onDismiss = { editingStudent = null },
            onConfirm = { name, roll, div ->
                viewModel.updateStudent(student.copy(name = name, rollNo = roll, division = div))
                editingStudent = null
            }
        )
    }
}

@Composable
fun StudentFormDialog(
    title: String,
    initialName: String = "",
    initialRoll: String = "",
    initialDiv: String = "CSE SE-A",
    onDismiss: () -> Unit,
    onConfirm: (name: String, roll: String, div: String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var roll by remember { mutableStateOf(initialRoll) }
    var div by remember { mutableStateOf(initialDiv) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = roll,
                    onValueChange = { roll = it },
                    label = { Text("Roll Number") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = div,
                    onValueChange = { div = it },
                    label = { Text("Class / Division") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, roll, div) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextMuted)
            }
        }
    )
}
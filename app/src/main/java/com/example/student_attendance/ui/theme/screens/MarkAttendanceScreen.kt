package com.example.student_attendance.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.ui.theme.*
import com.example.student_attendance.viewmodel.AttendanceViewModel

@Composable
fun MarkAttendanceScreen(viewModel: AttendanceViewModel) {
    val context = LocalContext.current
    val studentList: List<Student> by viewModel.students.collectAsState(initial = emptyList())

    val totalCount = studentList.size
    val presentCount = studentList.count { it.isPresent }
    val absentCount = studentList.count { !it.isPresent }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    Toast.makeText(context, "Attendance Saved Successfully!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("SAVE ATTENDANCE", fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightGreyBg),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NavyDark)
                        .padding(20.dp)
                ) {
                    Text("Attendance", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Mark and manage student attendance", color = TextMuted, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(color = NavySurface, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("SELECTED CLASS", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text("CSE • Second Year • Division A", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Text("CHANGE", color = PrimaryBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("TODAY'S ATTENDANCE", fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                                Text("Aug 19, 2026", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = InputBg) {
                                Text("LATE MARKING OFF", fontSize = 9.sp, color = TextMuted, modifier = Modifier.padding(6.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            StatTile("TOTAL", "$totalCount", InputBg, TextDark, Modifier.weight(1f))
                            StatTile("PRESENT", "$presentCount", Color(0xFFDCFCE7), AccentGreen, Modifier.weight(1f))
                            StatTile("ABSENT", "$absentCount", Color(0xFFFEE2E2), AccentRed, Modifier.weight(1f))
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Mark Students",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(studentList, key = { it.id }) { student ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
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
                            Surface(shape = RoundedCornerShape(6.dp), color = InputBg) {
                                Text(student.rollNo, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(6.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(student.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(
                                    text = "Status: ${if (student.isPresent) "Present" else "Absent"}",
                                    fontSize = 10.sp,
                                    color = if (student.isPresent) AccentGreen else AccentRed
                                )
                            }
                        }
                        Switch(
                            checked = student.isPresent,
                            onCheckedChange = { isChecked ->
                                viewModel.toggleAttendance(student.id, isChecked)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = AccentGreen,
                                uncheckedTrackColor = InputBg
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatTile(title: String, count: String, bg: Color, textCol: Color, modifier: Modifier) {
    Surface(shape = RoundedCornerShape(8.dp), color = bg, modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = textCol)
            Text(count, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textCol)
        }
    }
}
package com.example.student_attendance.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.student_attendance.viewmodel.ReportViewModel

@Composable
fun ReportScreen(viewModel: ReportViewModel) {
    val studentList: List<Student> by viewModel.students.collectAsState(initial = emptyList())

    val totalCount = studentList.size
    val presentCount = studentList.count { it.isPresent }
    val absentCount = studentList.count { !it.isPresent }
    val attendanceRate = if (totalCount > 0) (presentCount * 100) / totalCount else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreyBg)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyDark)
                .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Reports", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("View attendance performance", color = TextMuted, fontSize = 12.sp)
                }
                IconButton(onClick = { }, modifier = Modifier.background(NavySurface, CircleShape)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_pie_chart_24),
                        contentDescription = "Reports",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(shape = RoundedCornerShape(8.dp), color = PrimaryBlue) {
                    Text("CSE SE-A", color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                }
                Surface(shape = RoundedCornerShape(8.dp), color = NavySurface) {
                    Text("Today", color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                }
                Surface(shape = RoundedCornerShape(8.dp), color = NavySurface) {
                    Text("This Week", color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                }
            }
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
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column { Text("TOTAL", fontSize = 10.sp, color = TextMuted); Text("$totalCount", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                        Column { Text("PRESENT", fontSize = 10.sp, color = AccentGreen); Text("$presentCount", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AccentGreen) }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column { Text("ABSENT", fontSize = 10.sp, color = AccentRed); Text("$absentCount", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AccentRed) }
                        Column { Text("RATE", fontSize = 10.sp, color = PrimaryBlue); Text("$attendanceRate%", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryBlue) }
                    }
                }
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(InputBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$attendanceRate%", fontWeight = FontWeight.Bold, color = PrimaryBlue)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Student-wise Report", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("FILTERS", fontSize = 11.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))

        studentList.forEach { student ->
            val score = if (student.isPresent) "100%" else "0%"
            val initials = if (student.name.isNotBlank()) student.name.take(2).uppercase() else "ST"
            StudentPerfCard(
                initials = initials,
                name = student.name,
                roll = student.rollNo,
                score = score
            )
        }
    }
}

@Composable
fun StudentPerfCard(initials: String, name: String, roll: String, score: String) {
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
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(InputBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(initials, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PrimaryBlue)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Roll No: $roll", fontSize = 10.sp, color = TextMuted)
                }
            }
            Text("$score PERFORMANCE", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = AccentGreen)
        }
    }
}
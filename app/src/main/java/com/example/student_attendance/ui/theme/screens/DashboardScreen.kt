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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_attendance.R
import com.example.student_attendance.data.local.entity.Student
import com.example.student_attendance.ui.theme.*
import com.example.student_attendance.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val studentList: List<Student> by viewModel.students.collectAsState(initial = emptyList())

    val totalStudents = studentList.size
    val presentToday = studentList.count { it.isPresent }
    val attendanceRate = if (totalStudents > 0) (presentToday * 100) / totalStudents else 0

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("TeacherTrack", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Attendance Dashboard", color = TextMuted, fontSize = 12.sp)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.background(NavySurface, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_home_24),
                            contentDescription = "Notifications",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(PrimaryBlue, CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Wednesday, August 19, 2026", color = TextMuted, fontSize = 12.sp)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Welcome Back 👋", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("Here is today's attendance overview", fontSize = 12.sp, color = TextMuted)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricBox(
                        title = "TOTAL STUDENTS",
                        value = "$totalStudents",
                        sub = "+$totalStudents enrolled",
                        painter = painterResource(id = R.drawable.baseline_groups_24),
                        iconTint = PrimaryBlue,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "PRESENT TODAY",
                        value = "$presentToday",
                        sub = "$attendanceRate% attendance rate",
                        painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                        iconTint = AccentGreen,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Weekly Attendance Trend", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(InputBg, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Attendance Graph (Mon - Fri)", color = TextMuted, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Upcoming Classes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("See All", color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(12.dp))

                ClassCard(
                    time = "10:00 AM",
                    title = "Computer Science 101",
                    subtitle = "Section A • Room 302",
                    isQueued = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                ClassCard(
                    time = "01:30 PM",
                    title = "Database Management",
                    subtitle = "Section B • Lab 4",
                    isQueued = true
                )
            }
        }
    }
}

@Composable
fun MetricBox(
    title: String,
    value: String,
    sub: String,
    painter: Painter,
    iconTint: Color,
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = InputBg
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.SemiBold)
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(sub, fontSize = 10.sp, color = iconTint)
        }
    }
}

@Composable
fun ClassCard(time: String, title: String, subtitle: String, isQueued: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightGreyBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = LightYellow
                ) {
                    Text(time, fontSize = 10.sp, color = DarkYellow, fontWeight = FontWeight.Bold, modifier = Modifier.padding(6.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(subtitle, fontSize = 11.sp, color = TextMuted)
                }
            }
            if (isQueued) {
                Surface(shape = RoundedCornerShape(6.dp), color = InputBg) {
                    Text("QUEUED", fontSize = 10.sp, color = TextMuted, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            } else {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("TAKE ATTENDANCE", fontSize = 10.sp)
                }
            }
        }
    }
}
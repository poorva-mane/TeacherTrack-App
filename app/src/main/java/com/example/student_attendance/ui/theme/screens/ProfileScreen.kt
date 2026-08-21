package com.example.student_attendance.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_attendance.R
import com.example.student_attendance.ui.theme.*

@Composable
fun ProfileScreen(onLogout: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreyBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyDark)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(PrimaryBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Prof. John Doe", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Department of Computer Science", color = TextMuted, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.baseline_home_24),
                    title = "Assigned Classes"
                )
                HorizontalDivider(color = InputBg)
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                    title = "Schedule"
                )
                HorizontalDivider(color = InputBg)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLogout() }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = null,
                        tint = AccentRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Logout", color = AccentRed, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(painter: Painter, title: String, tint: Color = TextDark) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painter, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = tint, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}
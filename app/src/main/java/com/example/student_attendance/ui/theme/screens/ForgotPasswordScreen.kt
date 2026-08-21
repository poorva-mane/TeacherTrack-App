package com.example.student_attendance.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_attendance.R
import com.example.student_attendance.ui.theme.*

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    onSendResetLink: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("TeacherTrack", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Attendance Management System", color = TextMuted, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Key Icon Badge
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFFE0E7FF), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Update 'ic_key' with your drawable name (or use a key icon)
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_key_24),
                        contentDescription = "Forgot Password",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Forgot Password", color = TextDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Enter your registered email address and we will send you a password reset link.",
                    color = TextMuted,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    placeholder = { Text("teacher@university.edu") },
                    leadingIcon = {
                        // Update 'ic_email' with your drawable name
                        Icon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = InputBg,
                        focusedContainerColor = InputBg
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onSendResetLink(email) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Send Reset Link", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .clickable { onBackToLogin() }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Update 'ic_arrow_back' with your drawable name
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = TextDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Back to Login",
                        color = TextDark,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
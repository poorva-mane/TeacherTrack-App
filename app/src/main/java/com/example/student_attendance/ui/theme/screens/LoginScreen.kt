package com.example.student_attendance.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_attendance.ui.theme.CardBackground
import com.example.student_attendance.ui.theme.InputBg
import com.example.student_attendance.ui.theme.NavyDark
import com.example.student_attendance.ui.theme.PrimaryBlue
import com.example.student_attendance.ui.theme.TextDark
import com.example.student_attendance.ui.theme.TextMuted


@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgot: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                Text("Welcome Back 👋", color = TextDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Login to continue", color = TextMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp, bottom = 24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    placeholder = { Text("teacher@university.edu") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = TextMuted) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = InputBg, focusedContainerColor = InputBg),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted) },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = InputBg, focusedContainerColor = InputBg),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Forgot Password?",
                    color = PrimaryBlue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                        .clickable { onNavigateToForgot() }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text("Don't have an account? ", color = TextMuted, fontSize = 13.sp)
                    Text(
                        "Sign Up",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { onNavigateToSignUp() }
                    )
                }
            }
        }
    }
}
package com.example.student_attendance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.student_attendance.ui.theme.CardBackground
import com.example.student_attendance.ui.theme.PrimaryBlue
import com.example.student_attendance.ui.theme.Student_AttendanceTheme
import com.example.student_attendance.ui.theme.TextMuted
import com.example.student_attendance.ui.theme.screens.*
import com.example.student_attendance.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Student_AttendanceTheme {
                TeacherTrackApp()
            }
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object SignUp : AuthScreen("signup")
    object ForgotPassword : AuthScreen("forgot_password")
    object Main : AuthScreen("main_graph")
}

sealed class BottomTab(val route: String, val title: String, val iconRes: Int) {
    object Home : BottomTab("tab_home", "Home", R.drawable.baseline_home_24)
    object Students : BottomTab("tab_students", "Students", R.drawable.baseline_groups_24)
    object Attendance : BottomTab("tab_attendance", "Attendance", R.drawable.baseline_calendar_month_24)
    object Reports : BottomTab("tab_reports", "Reports", R.drawable.baseline_pie_chart_24)
    object Profile : BottomTab("tab_profile", "Profile", R.drawable.person)
}

@Composable
fun TeacherTrackApp() {
    val context = LocalContext.current
    val app = context.applicationContext as AttendanceApplication
    val factory = AttendanceViewModelFactory(app.repository)

    val rootNavController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel(factory = factory)

    NavHost(
        navController = rootNavController,
        startDestination = AuthScreen.Login.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(AuthScreen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = { rootNavController.navigate(AuthScreen.SignUp.route) },
                onNavigateToForgot = { rootNavController.navigate(AuthScreen.ForgotPassword.route) },
                onLoginSuccess = {
                    rootNavController.navigate(AuthScreen.Main.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AuthScreen.SignUp.route) {
            SignupScreen(
                onNavigateToLogin = { rootNavController.popBackStack() },
                onSignUpSuccess = {
                    rootNavController.navigate(AuthScreen.Main.route) {
                        popUpTo(AuthScreen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackToLogin = { rootNavController.popBackStack() },
                onSendResetLink = { rootNavController.popBackStack() }
            )
        }

        composable(AuthScreen.Main.route) {
            MainContainerScreen(
                factory = factory,
                onLogout = {
                    authViewModel.logout()
                    rootNavController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun MainContainerScreen(
    factory: AttendanceViewModelFactory,
    onLogout: () -> Unit = {}
) {
    val tabNavController = rememberNavController()

    val attendanceViewModel: AttendanceViewModel = viewModel(factory = factory)
    val dashboardViewModel: DashboardViewModel = viewModel(factory = factory)
    val reportViewModel: ReportViewModel = viewModel(factory = factory)

    val tabs = listOf(
        BottomTab.Home,
        BottomTab.Students,
        BottomTab.Attendance,
        BottomTab.Reports,
        BottomTab.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = CardBackground) {
                val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                tabs.forEach { tab ->
                    val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true

                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.iconRes),
                                contentDescription = tab.title,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = { Text(tab.title, fontSize = 10.sp) },
                        selected = selected,
                        onClick = {
                            tabNavController.navigate(tab.route) {
                                popUpTo(tabNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted,
                            indicatorColor = Color(0xFFEFF6FF)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = BottomTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomTab.Home.route) { DashboardScreen(dashboardViewModel) }
            composable(BottomTab.Students.route) { StudentListScreen(attendanceViewModel) }
            composable(BottomTab.Attendance.route) { MarkAttendanceScreen(attendanceViewModel) }
            composable(BottomTab.Reports.route) { ReportScreen(reportViewModel) }
            composable(BottomTab.Profile.route) { ProfileScreen(onLogout = onLogout) }
        }
    }
}
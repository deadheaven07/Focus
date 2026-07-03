package com.focusflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.focusflow.presentation.authentication.LoginScreen
import com.focusflow.presentation.dashboard.DashboardScreen
import com.focusflow.presentation.focus.FocusHistoryScreen
import com.focusflow.presentation.focus.FocusScreen
import com.focusflow.presentation.habits.AddHabitScreen
import com.focusflow.presentation.habits.HabitListScreen
import com.focusflow.presentation.profile.ProfileScreen
import com.focusflow.presentation.theme.FocusFlowTheme
import com.focusflow.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // grab the user session so we know where to start
            val mainViewModel: MainViewModel = hiltViewModel()
            val currentUser by mainViewModel.currentUser.collectAsState()
            val isDarkMode by mainViewModel.isDarkMode.collectAsState()

            FocusFlowTheme(darkTheme = isDarkMode ?: androidx.compose.foundation.isSystemInDarkTheme()) {
                val navController = rememberNavController()
                
                Scaffold(
                    bottomBar = {
                        // only show the bottom bar if they're actually logged in
                        if (currentUser != null) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentRoute == Screen.Dashboard.route,
                                    onClick = { navController.navigate(Screen.Dashboard.route) },
                                    icon = { Icon(Icons.Default.Home, "Dashboard") },
                                    label = { Text("Home") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == Screen.HabitList.route,
                                    onClick = { navController.navigate(Screen.HabitList.route) },
                                    icon = { Icon(Icons.Default.DateRange, "Habits") },
                                    label = { Text("Habits") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == Screen.FocusSession.route,
                                    onClick = { navController.navigate(Screen.FocusSession.route) },
                                    icon = { Icon(Icons.Default.PlayArrow, "Focus") },
                                    label = { Text("Focus") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == Screen.Profile.route,
                                    onClick = { navController.navigate(Screen.Profile.route) },
                                    icon = { Icon(Icons.Default.Person, "Profile") },
                                    label = { Text("Profile") }
                                )
                            }
                        }
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // start at login if we don't have a user, otherwise dashboard
                        val startDestination = if (currentUser == null) Screen.Login.route else Screen.Dashboard.route

                        NavHost(
                            navController = navController,
                            startDestination = startDestination,
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                ) + fadeIn(animationSpec = tween(700))
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                ) + fadeOut(animationSpec = tween(700))
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(700)
                                ) + fadeIn(animationSpec = tween(700))
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(700)
                                ) + fadeOut(animationSpec = tween(700))
                            }
                        ) {
                            composable(Screen.Login.route) {
                                LoginScreen(
                                    onLoginSuccess = {
                                        navController.navigate(Screen.Dashboard.route) {
                                            // clear the backstack so they can't go back to login
                                            popUpTo(Screen.Login.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(Screen.Dashboard.route) {
                                DashboardScreen(
                                    onSeeAllHabits = {
                                        navController.navigate(Screen.HabitList.route)
                                    }
                                )
                            }
                            composable(Screen.HabitList.route) {
                                HabitListScreen(
                                    onAddHabitClick = {
                                        navController.navigate(Screen.AddHabit.route)
                                    }
                                )
                            }
                            composable(Screen.AddHabit.route) {
                                AddHabitScreen(
                                    onHabitAdded = {
                                        navController.popBackStack()
                                    },
                                    onBack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            composable(Screen.FocusSession.route) {
                                FocusScreen(
                                    onSeeHistory = {
                                        navController.navigate(Screen.FocusHistory.route)
                                    }
                                )
                            }
                            composable(Screen.FocusHistory.route) {
                                FocusHistoryScreen()
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

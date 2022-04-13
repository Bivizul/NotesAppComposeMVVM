package com.bivizul.notesappcomposemvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bivizul.notesappcomposemvvm.MainViewModel
import com.bivizul.notesappcomposemvvm.screens.AddScreen
import com.bivizul.notesappcomposemvvm.screens.MainScreen
import com.bivizul.notesappcomposemvvm.screens.NoteScreen
import com.bivizul.notesappcomposemvvm.screens.StartScreen

// Изолированный класс с ссылками на экраны
sealed class NavRoute(val route: String) {
    object StartScreen : NavRoute("start_screen")
    object MainScreen : NavRoute("main_screen")
    object AddScreen : NavRoute("add_screen")
    object NoteScreen : NavRoute("note_screen")
}

@Composable
fun NotesNavHostController(mainViewModel: MainViewModel) {
    // Создаем NavController
    val navController = rememberNavController()

    // Задаем navController и стартовый экран
    NavHost(navController = navController, startDestination = NavRoute.StartScreen.route) {
        // Реализуем навигацию
        composable(NavRoute.StartScreen.route) { StartScreen(navController = navController,viewModel = mainViewModel) }
        composable(NavRoute.MainScreen.route) { MainScreen(navController = navController,viewModel = mainViewModel) }
        composable(NavRoute.AddScreen.route) { AddScreen(navController = navController,viewModel = mainViewModel) }
        composable(NavRoute.NoteScreen.route) { NoteScreen(navController = navController,viewModel = mainViewModel) }
    }

}
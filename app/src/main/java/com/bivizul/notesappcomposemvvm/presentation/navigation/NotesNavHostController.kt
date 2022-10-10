package com.bivizul.notesappcomposemvvm.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bivizul.notesappcomposemvvm.MainViewModel
import com.bivizul.notesappcomposemvvm.presentation.screens.AddScreen
import com.bivizul.notesappcomposemvvm.presentation.screens.MainScreen
import com.bivizul.notesappcomposemvvm.presentation.screens.NoteScreen
import com.bivizul.notesappcomposemvvm.presentation.screens.StartScreen
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.ID
import com.bivizul.notesappcomposemvvm.utils.Constants.Screens.ADD_SCREEN
import com.bivizul.notesappcomposemvvm.utils.Constants.Screens.MAIN_SCREEN
import com.bivizul.notesappcomposemvvm.utils.Constants.Screens.NOTE_SCREEN
import com.bivizul.notesappcomposemvvm.utils.Constants.Screens.START_SCREEN

// Изолированный класс с ссылками на экраны
sealed class NavRoute(val route: String) {
    object StartScreen : NavRoute(START_SCREEN)
    object MainScreen : NavRoute(MAIN_SCREEN)
    object AddScreen : NavRoute(ADD_SCREEN)
    object NoteScreen : NavRoute(NOTE_SCREEN)
}

@Composable
fun NotesNavHostController(mainViewModel: MainViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavRoute.StartScreen.route) {
        composable(NavRoute.StartScreen.route) {
            StartScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        composable(NavRoute.MainScreen.route) {
            MainScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        composable(NavRoute.AddScreen.route) {
            AddScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        composable(NavRoute.NoteScreen.route + "/{${ID}}") { backStackEntry ->
            NoteScreen(
                navController = navController,
                viewModel = mainViewModel,
                noteId = backStackEntry.arguments?.getString(ID)
            )
        }
    }

}
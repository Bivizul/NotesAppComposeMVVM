package com.bivizul.notesappcomposemvvm.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bivizul.notesappcomposemvvm.MainViewModel
import com.bivizul.notesappcomposemvvm.MainViewModelFactory
import com.bivizul.notesappcomposemvvm.navigation.NavRoute
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.FIREBASE_DATABASE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.ROOM_DATABASE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.WHATS_WILL_WE_USE
import com.bivizul.notesappcomposemvvm.utils.TYPE_FIREBASE
import com.bivizul.notesappcomposemvvm.utils.TYPE_ROOM

// Создаем стартовый экран
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

    // Переменные для обработки кликов
    val context = LocalContext.current
    val mainViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = WHATS_WILL_WE_USE)
            // Room database
            Button(
                onClick = {
                    // Передает информацию - на какую кнопку было нажатие
                    // Навигацию передаем в callback
                    mainViewModel.initDatabase(TYPE_ROOM){
                        navController.navigate(route = NavRoute.MainScreen.route)
                    }


                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = ROOM_DATABASE)
            }
            // Firebase database
            Button(
                onClick = {
                    mainViewModel.initDatabase(TYPE_FIREBASE){
                        navController.navigate(route = NavRoute.MainScreen.route)
                    }

                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = FIREBASE_DATABASE)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun previewStartScreen() {
    NotesAppComposeMVVMTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(
                factory = MainViewModelFactory(context.applicationContext as Application)
            )
        StartScreen(navController = rememberNavController(), viewModel = mainViewModel)
    }
}
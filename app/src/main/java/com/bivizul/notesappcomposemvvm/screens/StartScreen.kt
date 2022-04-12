package com.bivizul.notesappcomposemvvm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bivizul.notesappcomposemvvm.navigation.NavRoute
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme

// Создаем стартовый экран
@Composable
fun StartScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "What will we use?")
            Button(
                onClick = {
                    navController.navigate(route = NavRoute.MainScreen.route)
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Room database")
            }
            Button(
                onClick = {
                    navController.navigate(route = NavRoute.MainScreen.route)
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Firebase database")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun previewStartScreen() {
    NotesAppComposeMVVMTheme {
        StartScreen(navController = rememberNavController())
    }
}
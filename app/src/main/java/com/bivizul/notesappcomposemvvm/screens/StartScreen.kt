package com.bivizul.notesappcomposemvvm.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

// Создаем стартовый экран
@Composable
fun StartScreen(navController: NavHostController) {
    Text(text = "Start Screen")
}
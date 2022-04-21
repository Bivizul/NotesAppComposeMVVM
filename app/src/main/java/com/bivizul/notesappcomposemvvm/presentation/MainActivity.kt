package com.bivizul.notesappcomposemvvm.presentation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bivizul.notesappcomposemvvm.MainViewModel
import com.bivizul.notesappcomposemvvm.MainViewModelFactory
import com.bivizul.notesappcomposemvvm.presentation.navigation.NavRoute
import com.bivizul.notesappcomposemvvm.presentation.navigation.NotesNavHostController
import com.bivizul.notesappcomposemvvm.ui.theme.Green
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.NOTES_APP
import com.bivizul.notesappcomposemvvm.utils.DB_TYPE

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppComposeMVVMTheme {
                val context = LocalContext.current
                val mainViewModel: MainViewModel =
                    viewModel(
                        factory = MainViewModelFactory(context.applicationContext as Application)
                    )
                val navController = rememberNavController()
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = NOTES_APP)
                                // Выход из базы данных
                                if(DB_TYPE.value.isNotEmpty()){
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "",
                                        modifier = Modifier.clickable {
                                            mainViewModel.signOut {
                                                navController.navigate(NavRoute.StartScreen.route){
                                                    // Не дадим вернуться после выхода
                                                    popUpTo(NavRoute.StartScreen.route){
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                            }
                        },
                        backgroundColor = Green,
                        contentColor = Color.White,
                        elevation = 12.dp
                    )
                }, content = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        NotesNavHostController(mainViewModel, navController)
                    }
                })
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        NotesAppComposeMVVMTheme {

        }
    }
}
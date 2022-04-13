package com.bivizul.notesappcomposemvvm

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bivizul.notesappcomposemvvm.navigation.NotesNavHostController
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme

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
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Notes App")
                        },
                        backgroundColor = Color.Blue,
                        contentColor = Color.White,
                        elevation = 12.dp
                    )
                }, content = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        NotesNavHostController(mainViewModel)
                    }
                })
            }
        }
    }

//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        NotesAppComposeMVVMTheme {
//
//        }
//    }
}
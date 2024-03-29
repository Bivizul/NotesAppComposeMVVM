package com.bivizul.notesappcomposemvvm.presentation.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bivizul.notesappcomposemvvm.MainViewModel
import com.bivizul.notesappcomposemvvm.MainViewModelFactory
import com.bivizul.notesappcomposemvvm.data.model.Note
import com.bivizul.notesappcomposemvvm.presentation.navigation.NavRoute
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.ADD_ICONS
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.EMPTY
import com.bivizul.notesappcomposemvvm.utils.DB_TYPE
import com.bivizul.notesappcomposemvvm.utils.TYPE_FIREBASE
import com.bivizul.notesappcomposemvvm.utils.TYPE_ROOM

// Создаем главный экран
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(route = NavRoute.AddScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ADD_ICONS,
                    tint = Color.White
                )
            }
        }
    ) {
        LazyColumn {
            items(notes) { note ->
                NoteItem(note = note, navController = navController)
            }
        }


    }

}

@Composable
fun NoteItem(note: Note, navController: NavHostController) {
    val noteId = when(DB_TYPE.value){
        TYPE_FIREBASE -> note.firebaseId
        TYPE_ROOM -> note.id
        else -> EMPTY
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .clickable {
                navController.navigate(route = NavRoute.NoteScreen.route + "/${noteId}")
            },
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = note.subtitle
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    NotesAppComposeMVVMTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(
                factory = MainViewModelFactory(context.applicationContext as Application)
            )
        MainScreen(navController = rememberNavController(), viewModel = mainViewModel)
    }
}
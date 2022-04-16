package com.bivizul.notesappcomposemvvm.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.bivizul.notesappcomposemvvm.model.Note
import com.bivizul.notesappcomposemvvm.navigation.NavRoute
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.DELETE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.EDIT_NOTE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.EMPTY
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.NAV_BACK
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.SUBTITLE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.TITLE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.UPDATE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.UPDATE_NOTE
import com.bivizul.notesappcomposemvvm.utils.DB_TYPE
import com.bivizul.notesappcomposemvvm.utils.TYPE_FIREBASE
import com.bivizul.notesappcomposemvvm.utils.TYPE_ROOM
import kotlinx.coroutines.launch

// Создаем экран заметки
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {

    // Список всех заметок
    val notes = viewModel.readAllNotes().observeAsState(listOf()).value

    // Текущая заметка
    val note = when(DB_TYPE){
        TYPE_ROOM -> {
            notes.firstOrNull { it.id == noteId?.toInt() } ?: Note()
        }
        TYPE_FIREBASE -> {
            notes.firstOrNull{it.firebaseId == noteId} ?: Note()
        }
        else -> Note()
    }

    // Состояние ModalSheet
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf(EMPTY) }
    var subtitle by remember { mutableStateOf(EMPTY) }

    // Верстка ModalSheet
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        // Верстка контента
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(
                        text = EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = TITLE) },
                        isError = title.isEmpty()
                    )
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text(text = SUBTITLE) },
                        isError = subtitle.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            viewModel.updateNote(
                                note = Note(
                                    id = note.id,
                                    title = title,
                                    subtitle = subtitle,
                                    firebaseId = note.firebaseId
                                )
                            ) {
                                navController.navigate(NavRoute.MainScreen.route)
                            }
                        }
                    ) {
                        Text(text = UPDATE_NOTE)

                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                        Text(
                            text = note.subtitle,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                title = note.title
                                subtitle = note.subtitle
                                bottomSheetState.show()
                            }
                        }
                    ) {
                        Text(text = UPDATE)
                    }
                    Button(
                        onClick = {
                            viewModel.deleteNote(note = note){
                                navController.navigate(NavRoute.MainScreen.route)
                            }
                        }
                    ) {
                        Text(text = DELETE)
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate(route = NavRoute.MainScreen.route)
                    }
                ) {
                    Text(text = NAV_BACK)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteScreen() {
    NotesAppComposeMVVMTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(
                factory = MainViewModelFactory(context.applicationContext as Application)
            )
        NoteScreen(
            navController = rememberNavController(),
            viewModel = mainViewModel,
            noteId = "1"
        )
    }
}
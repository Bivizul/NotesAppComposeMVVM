package com.bivizul.notesappcomposemvvm.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.bivizul.notesappcomposemvvm.navigation.NavRoute
import com.bivizul.notesappcomposemvvm.ui.theme.NotesAppComposeMVVMTheme
import com.bivizul.notesappcomposemvvm.utils.*
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.FIREBASE_DATABASE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.ROOM_DATABASE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.WHATS_WILL_WE_USE
import kotlinx.coroutines.launch

// Создаем стартовый экран
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

    // Состояние ModalSheet
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY) }

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
                        text = Constants.Keys.LOG_IN,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        label = { Text(text = Constants.Keys.LOGIN_TEXT) },
                        isError = login.isEmpty()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = Constants.Keys.PASSWORD_TEXT) },
                        isError = password.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            LOGIN = login
                            PASSWORD = password
                            viewModel.initDatabase(TYPE_FIREBASE){
                                Log.d("checkData", "Auth success")
//                                navController.navigate(NavRoute.MainScreen.route)
                            }
                        },
                        enabled = login.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.SIGN_IN)

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
                Text(text = WHATS_WILL_WE_USE)
                // Room database
                Button(
                    onClick = {
                        // Передает информацию - на какую кнопку было нажатие
                        // Навигацию передаем в callback
                        viewModel.initDatabase(TYPE_ROOM) {
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
                        // При нажатии отобразим bottomSheetState в отдельном coroutineScope
                        coroutineScope.launch {
                            bottomSheetState.show()
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
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    NotesAppComposeMVVMTheme {
        val context = LocalContext.current
        val mainViewModel: MainViewModel =
            viewModel(
                factory = MainViewModelFactory(context.applicationContext as Application)
            )
        StartScreen(navController = rememberNavController(), viewModel = mainViewModel)
    }
}

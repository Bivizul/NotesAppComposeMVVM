package com.bivizul.notesappcomposemvvm.utils

import androidx.compose.runtime.mutableStateOf
import com.bivizul.notesappcomposemvvm.domain.repository.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"
const val FIREBASE_ID = "firebaseId"

lateinit var REPOSITORY: DatabaseRepository
lateinit var LOGIN: String
lateinit var PASSWORD: String
var DB_TYPE = mutableStateOf("")

object Constants {
    object Keys {
        const val NOTES_DATABASE = "notes_database"
        const val NOTES_TABLE = "notes_table"
        const val ADD_NEW_NOTE = "Add new note"
        const val NOTE_TITLE = "Note title"
        const val NOTE_SUBTITLE = "Note subtitle"
        const val ADD_NOTE = "Add note"
        const val ADD_ICONS = "Add Icons"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val WHATS_WILL_WE_USE = "What will we use?"
        const val ROOM_DATABASE = "Room database"
        const val FIREBASE_DATABASE = "Firebase database"
        const val NOTES_APP = "Notes"
        const val UNKNOWN_VIEWMODEL_CLASS = "Unknown ViewModel Class"
        const val ID = "Id"
        const val NONE = "none"
        const val UPDATE = "UPDATE"
        const val DELETE = "DELETE"
        const val NAV_BACK = "BACK"
        const val EDIT_NOTE = "Edit note"
        const val EMPTY = ""
        const val UPDATE_NOTE = "Update note"
        const val SIGN_IN = "Sign In"
        const val LOG_IN = "Log In"
        const val LOGIN_TEXT = "Login"
        const val PASSWORD_TEXT = "Password"
    }

    object Screens {
        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"
    }
}
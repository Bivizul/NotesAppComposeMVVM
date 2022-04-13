package com.bivizul.notesappcomposemvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bivizul.notesappcomposemvvm.database.room.AppRoomDatabase
import com.bivizul.notesappcomposemvvm.database.room.repository.RoomRepository
import com.bivizul.notesappcomposemvvm.model.Note
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.UNKNOWN_VIEWMODEL_CLASS
import com.bivizul.notesappcomposemvvm.utils.REPOSITORY
import com.bivizul.notesappcomposemvvm.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    // Инициализация базы данных
    fun initDatabase(type: String, onSuccess: () -> Unit) {
        Log.d("checkData", "MainViewModel initDatabase with type $type")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
        }
    }

    // Добавление заметки в локальную базу данных
    fun addNote(note: Note, onSuccess: () -> Unit) {
        // Запускаем в потоке InputOutput создачу заметки
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note) {
                // Запускаем в главном потоке callback
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    // Обновление заметки
    fun updateNote(note: Note,onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.update(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSuccess()
                }
            }
        }
    }

    // Удаление заметки
    fun deleteNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.delete(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSuccess()
                }
            }
        }
    }

    // Загрузка заметок из локальной базы данных Room
    fun readAllNotes() = REPOSITORY.readAll

}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException(UNKNOWN_VIEWMODEL_CLASS)
    }
}
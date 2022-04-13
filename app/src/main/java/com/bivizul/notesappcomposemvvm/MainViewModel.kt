package com.bivizul.notesappcomposemvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bivizul.notesappcomposemvvm.model.Note
import com.bivizul.notesappcomposemvvm.room.AppRoomDatabase
import com.bivizul.notesappcomposemvvm.room.repository.RoomRepository
import com.bivizul.notesappcomposemvvm.utils.REPOSITORY
import com.bivizul.notesappcomposemvvm.utils.TYPE_FIREBASE
import com.bivizul.notesappcomposemvvm.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

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
    fun addNote(note: Note, onSuccess: () -> Unit){
        // Запускаем в потоке InputOutput создачу заметки
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.create(note = note){
                // Запускаем в главном потоке callback
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
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
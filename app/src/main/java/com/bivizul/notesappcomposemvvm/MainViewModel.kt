package com.bivizul.notesappcomposemvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bivizul.notesappcomposemvvm.model.Note
import com.bivizul.notesappcomposemvvm.utils.TYPE_FIREBASE
import com.bivizul.notesappcomposemvvm.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val notesTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }
    // Хранит тип базы данных c Default
    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }

    // Создадим список заметок
    init {
        notesTest.value =
            when(dbType.value){
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Note 1", subtitle = "Subtitle for note 1"),
                        Note(title = "Note 2", subtitle = "Subtitle for note 2"),
                        Note(title = "Note 3", subtitle = "Subtitle for note 3"),
                        Note(title = "Note 4", subtitle = "Subtitle for note 4")
                    )
                }
                TYPE_FIREBASE -> listOf()
                else -> listOf()
            }
    }

    // Инициализация базы данных
    fun initDatabase(type: String) {
        // Присваеваем тип базы данных при нажатии на кнопку на стартовом экране
        dbType.value = type

        Log.d("checkData","MainViewModel initDatabase with type $type")
    }

}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
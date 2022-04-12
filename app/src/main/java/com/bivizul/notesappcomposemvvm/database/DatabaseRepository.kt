package com.bivizul.notesappcomposemvvm.database

import androidx.lifecycle.LiveData
import com.bivizul.notesappcomposemvvm.model.Note

interface DatabaseRepository {

    // Считывание всех данных из базы данных
    val readAll: LiveData<List<Note>>

    // suspend fun выполняются в отдельном потоке
    // Создание заметки
    suspend fun create(note: Note, onSuccess: () -> Unit)

    // Обновление заметки
    suspend fun update(note: Note, onSuccess: () -> Unit)

    // Удаление заметки
    suspend fun delete(note: Note, onSuccess: () -> Unit)
}
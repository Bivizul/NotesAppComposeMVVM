package com.bivizul.notesappcomposemvvm.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bivizul.notesappcomposemvvm.model.Note

// Data access object
// Здесь функции для работы с локальной базой данных
@Dao
interface NoteRoomDao {

    // Получение всех заметок
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>

    // suspend fun выполняются в отдельном потоке
    // Создание заметки
    @Insert
    suspend fun addNote(note: Note)

    // Обновление заметки
    @Update
    suspend fun updateNote(note: Note)

    // Удаление заметки
    @Delete
    suspend fun deleteNote(note: Note)
}
package com.bivizul.notesappcomposemvvm.database.room.repository

import androidx.lifecycle.LiveData
import com.bivizul.notesappcomposemvvm.database.DatabaseRepository
import com.bivizul.notesappcomposemvvm.database.room.dao.NoteRoomDao
import com.bivizul.notesappcomposemvvm.model.Note


// Локальный репозиторий
class RoomRepository(private val noteRoomDao: NoteRoomDao) : DatabaseRepository {

    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note)
        onSuccess()
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note)
        onSuccess()
    }
}
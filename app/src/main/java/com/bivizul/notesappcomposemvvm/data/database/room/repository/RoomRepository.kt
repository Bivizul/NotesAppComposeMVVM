package com.bivizul.notesappcomposemvvm.data.database.room.repository

import androidx.lifecycle.LiveData
import com.bivizul.notesappcomposemvvm.domain.repository.DatabaseRepository
import com.bivizul.notesappcomposemvvm.data.database.room.dao.NoteRoomDao
import com.bivizul.notesappcomposemvvm.data.model.Note


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

    override fun signOut() {}
}
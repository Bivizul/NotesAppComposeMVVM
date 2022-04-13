package com.bivizul.notesappcomposemvvm.room.repository

import androidx.lifecycle.LiveData
import com.bivizul.notesappcomposemvvm.database.DatabaseRepository
import com.bivizul.notesappcomposemvvm.model.Note
import com.bivizul.notesappcomposemvvm.room.dao.NoteRoomDao

<<<<<<< HEAD
// Локальный репозиторий
=======
>>>>>>> origin/Branch-4
class RoomRepository(private val noteRoomDao: NoteRoomDao): DatabaseRepository {

    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note)
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note)
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note)
    }
}
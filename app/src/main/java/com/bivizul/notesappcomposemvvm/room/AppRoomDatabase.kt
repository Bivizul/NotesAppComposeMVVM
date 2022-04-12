package com.bivizul.notesappcomposemvvm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bivizul.notesappcomposemvvm.model.Note
import com.bivizul.notesappcomposemvvm.room.dao.NoteRoomDao

// База данных Room
@Database(entities = [Note::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        // Получение instance нашей базы данных
        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE as AppRoomDatabase
            } else {
                INSTANCE as AppRoomDatabase
            }
        }
    }
}
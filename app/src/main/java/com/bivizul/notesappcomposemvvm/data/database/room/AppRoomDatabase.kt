package com.bivizul.notesappcomposemvvm.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bivizul.notesappcomposemvvm.data.model.Note
import com.bivizul.notesappcomposemvvm.data.database.room.dao.NoteRoomDao
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.NOTES_DATABASE

// Локальная база данных Room
// База данных Room
@Database(entities = [Note::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {

        // @Volatile - значение этой переменной может изменяться другими потоками только атомарно
        // Придает атомарность - либо выполняется целиком, либо не выпоняется вообще
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        // Получение instance нашей базы данных
        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    NOTES_DATABASE
                ).build()
                INSTANCE as AppRoomDatabase
            } else {
                INSTANCE as AppRoomDatabase
            }
        }
    }
}
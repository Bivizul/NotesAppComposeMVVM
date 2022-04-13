package com.bivizul.notesappcomposemvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.NOTES_TABLE

// Аннотации необходимые для корректной работы локальной базы данных Room
@Entity(tableName = NOTES_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val subtitle: String
)

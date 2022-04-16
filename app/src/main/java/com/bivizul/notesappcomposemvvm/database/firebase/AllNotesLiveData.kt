package com.bivizul.notesappcomposemvvm.database.firebase

import androidx.lifecycle.LiveData
import com.bivizul.notesappcomposemvvm.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class AllNotesLiveData : LiveData<List<Note>>() {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference.child(mAuth.currentUser?.uid.toString())

    private val listener = object : ValueEventListener{

        override fun onDataChange(snapshot: DataSnapshot) {
            val notes = mutableListOf<Note>()
            // Заполняем наш лист
            snapshot.children.map {
                notes.add(it.getValue(Note::class.java) ?: Note())
            }
            value = notes
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    // Используется когда приложение запущено
    override fun onActive() {
        database.addValueEventListener(listener)
        super.onActive()
    }

    // Используется при выключенном приложении, чтобы мы не слушали что происходит в базе данных
    override fun onInactive() {
        database.removeEventListener(listener)
        super.onInactive()
    }
}
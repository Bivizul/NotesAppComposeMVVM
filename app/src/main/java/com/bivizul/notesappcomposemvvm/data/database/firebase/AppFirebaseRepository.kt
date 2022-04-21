package com.bivizul.notesappcomposemvvm.data.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.bivizul.notesappcomposemvvm.domain.repository.DatabaseRepository
import com.bivizul.notesappcomposemvvm.data.model.Note
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.SUBTITLE
import com.bivizul.notesappcomposemvvm.utils.Constants.Keys.TITLE
import com.bivizul.notesappcomposemvvm.utils.FIREBASE_ID
import com.bivizul.notesappcomposemvvm.utils.LOGIN
import com.bivizul.notesappcomposemvvm.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AppFirebaseRepository : DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference.child(mAuth.currentUser?.uid.toString())
    override val readAll: LiveData<List<Note>> = AllNotesLiveData()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
//        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()

        // Заполняем список
        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subtitle

        // Отправим заметку
        database.child(noteId).updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to add new note") }
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()

        // Заполняем список
        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to update note") }
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        database.child(note.firebaseId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to delete note") }
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}
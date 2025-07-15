package com.example.notesfrontend.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.notesfrontend.data.Note
import com.example.notesfrontend.data.NotesDatabase
import com.example.notesfrontend.repository.NoteRepository
import kotlinx.coroutines.launch

// PUBLIC_INTERFACE
class NoteDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: NoteRepository

    private val _noteId = MutableLiveData<Long>()
    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    init {
        val dao = NotesDatabase.getDatabase(application).noteDao()
        repo = NoteRepository(dao)
    }

    fun loadNote(id: Long) {
        _noteId.value = id
        viewModelScope.launch {
            _note.value = repo.getNoteById(id)
        }
    }

    fun delete(note: Note, finished: () -> Unit) {
        viewModelScope.launch {
            repo.delete(note)
            finished()
        }
    }
}

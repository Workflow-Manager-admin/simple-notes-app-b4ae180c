package com.example.notesfrontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesfrontend.data.Note
import com.example.notesfrontend.data.NotesDatabase
import com.example.notesfrontend.repository.NoteRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.Transformations

// PUBLIC_INTERFACE
class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: NoteRepository
    private val _searchQuery = MutableLiveData<String>("")

    val notes: LiveData<List<Note>>

    init {
        val dao = NotesDatabase.getDatabase(application).noteDao()
        repo = NoteRepository(dao)
        notes = Transformations.switchMap(_searchQuery) { q: String? ->
            if (q.isNullOrBlank()) repo.allNotes else repo.searchNotes(q)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun upsert(note: Note) = viewModelScope.launch { repo.upsert(note) }

    fun delete(note: Note) = viewModelScope.launch { repo.delete(note) }
}

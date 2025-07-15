package com.example.notesfrontend.repository

import androidx.lifecycle.LiveData
import com.example.notesfrontend.data.Note
import com.example.notesfrontend.data.NoteDao

// PUBLIC_INTERFACE
class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun searchNotes(query: String): LiveData<List<Note>> = noteDao.searchNotes(query)

    suspend fun upsert(note: Note) = noteDao.upsert(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
    suspend fun getNoteById(id: Long) = noteDao.getNoteById(id)
}

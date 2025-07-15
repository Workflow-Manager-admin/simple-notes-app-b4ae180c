package com.example.notesfrontend.data

import androidx.lifecycle.LiveData
import androidx.room.*

// PUBLIC_INTERFACE
@Dao
interface NoteDao {
    /** Get all notes sorted by most recent first. */
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    /** Insert or update note. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: Note): Long

    /** Delete note. */
    @Delete
    suspend fun delete(note: Note)

    /** Get note by id. */
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    /** Get notes matching the query in title/content. */
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchNotes(query: String): LiveData<List<Note>>
}

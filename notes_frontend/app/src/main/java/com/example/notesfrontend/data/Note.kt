package com.example.notesfrontend.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// PUBLIC_INTERFACE
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
) : Serializable

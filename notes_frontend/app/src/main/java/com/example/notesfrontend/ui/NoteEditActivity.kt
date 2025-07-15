package com.example.notesfrontend.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.R
import com.example.notesfrontend.data.Note
import com.example.notesfrontend.viewmodel.NotesViewModel
import android.content.Intent
import com.example.notesfrontend.viewmodel.NoteDetailViewModel
import androidx.activity.viewModels

class NoteEditActivity : AppCompatActivity() {
    private lateinit var titleEdit: EditText
    private lateinit var contentEdit: EditText

    private val notesViewModel: NotesViewModel by viewModels()
    private val detailViewModel: NoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme) // Force light
        setContentView(R.layout.activity_note_edit)

        titleEdit = findViewById(R.id.editNoteTitle)
        contentEdit = findViewById(R.id.editNoteContent)

        val editingNote = intent.getSerializableExtra("note") as? Note
        if (editingNote != null) {
            titleEdit.setText(editingNote.title)
            contentEdit.setText(editingNote.content)
        }

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarEdit).apply {
            title = if (editingNote == null) "Add Note" else "Edit Note"
            setNavigationOnClickListener { finish() }
            inflateMenu(R.menu.menu_save)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_save) {
                    val title = titleEdit.text.toString().trim()
                    val content = contentEdit.text.toString().trim()
                    if (title.isEmpty()) {
                        Toast.makeText(this@NoteEditActivity, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        val note = editingNote?.copy(title = title, content = content, timestamp = System.currentTimeMillis())
                            ?: Note(title = title, content = content)
                        notesViewModel.upsert(note)
                        finish()
                    }
                    true
                } else false
            }
        }
    }
}

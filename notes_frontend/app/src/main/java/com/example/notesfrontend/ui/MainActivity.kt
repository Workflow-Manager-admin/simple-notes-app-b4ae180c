package com.example.notesfrontend.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesfrontend.R
import com.example.notesfrontend.viewmodel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        // Setup RecyclerView
        noteAdapter = NoteAdapter(emptyList()) { note -> 
            val i = Intent(this, NoteDetailActivity::class.java)
            i.putExtra("noteId", note.id)
            startActivity(i)
        }

        findViewById<RecyclerView>(R.id.recyclerViewNotes).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }

        // Search bar
        findViewById<EditText>(R.id.editSearch).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                notesViewModel.setSearchQuery(s?.toString() ?: "")
            }
        })

        // Floating action button for new note
        findViewById<FloatingActionButton>(R.id.fabAddNote).setOnClickListener {
            startActivity(Intent(this, NoteEditActivity::class.java))
        }

        // Observe notes
        notesViewModel.notes.observe(this) { notes ->
            noteAdapter.updateNotes(notes)
            findViewById<View>(R.id.emptyState).visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}

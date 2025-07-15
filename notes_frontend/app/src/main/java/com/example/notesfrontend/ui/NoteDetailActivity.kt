package com.example.notesfrontend.ui

import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.R
import com.example.notesfrontend.data.Note
import com.example.notesfrontend.viewmodel.NoteDetailViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.viewModels

class NoteDetailActivity : AppCompatActivity() {
    private val detailViewModel: NoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_note_detail)

        val noteId = intent.getLongExtra("noteId", -1L)
        if (noteId == -1L) {
            finish(); return
        }

        detailViewModel.loadNote(noteId)
        detailViewModel.note.observe(this) { note ->
            if (note == null) {
                finish(); return@observe
            }
            findViewById<TextView>(R.id.detailTitle).text = note.title
            findViewById<TextView>(R.id.detailContent).text = note.content
            findViewById<TextView>(R.id.detailDate).text =
                SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault()).format(Date(note.timestamp))
        }

        // Toolbar for navigation and actions
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarDetail).apply {
            setNavigationOnClickListener { finish() }
            inflateMenu(R.menu.menu_detail)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_delete) {
                    confirmDelete()
                    true
                } else if (it.itemId == R.id.action_edit) {
                    val note = detailViewModel.note.value
                    if (note != null) {
                        val i = android.content.Intent(this@NoteDetailActivity, NoteEditActivity::class.java)
                        i.putExtra("note", note)
                        startActivity(i)
                        finish()
                    }
                    true
                } else false
            }
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Delete note?")
            .setMessage("This cannot be undone.")
            .setPositiveButton("Delete") { _: DialogInterface, _: Int ->
                detailViewModel.note.value?.let { note ->
                    detailViewModel.delete(note) { finish() }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

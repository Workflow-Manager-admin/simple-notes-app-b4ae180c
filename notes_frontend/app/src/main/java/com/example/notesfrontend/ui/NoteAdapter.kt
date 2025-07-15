package com.example.notesfrontend.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.R
import com.example.notesfrontend.data.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private var notes: List<Note>,
    private val onClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View, val onClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.noteTitle)
        private val content: TextView = itemView.findViewById(R.id.noteContent)
        private val date: TextView = itemView.findViewById(R.id.noteDate)
        private var curNote: Note? = null

        init {
            itemView.setOnClickListener {
                curNote?.let(onClick)
            }
        }

        fun bind(note: Note) {
            curNote = note
            title.text = note.title
            content.text = note.content.trim().lines().firstOrNull() ?: ""
            date.text = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault()).format(Date(note.timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size
}

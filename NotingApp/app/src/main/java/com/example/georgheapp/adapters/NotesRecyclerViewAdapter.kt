package com.example.georgheapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.georgheapp.R
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.NoteEditActivity
import com.example.georgheapp.data.NoteEditActivity.Companion.INTENT_EXTRA_NOTE_ID
import com.example.georgheapp.databinding.ItemListNoteBinding

class NoteRecyclerViewAdapter(val context: Context, val notes: ArrayList<Note>) :
    RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {
    private var modifiedNote: Int? = null

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListNoteBinding.bind(view)

        val title: TextView = binding.noteTitle
        val subtitle: TextView = binding.noteSubtitle
        val content: TextView = binding.noteContent
        val date: TextView = binding.noteDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_note, parent, false)

        // Assignem el layout al ViewHolder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        //Set Title
        holder.title.text = if (note.title?.length ?: 0 < 8)
            note.title
        else
            note.title?.subSequence(0, 8).toString() + "..."

        //Set Subtitle
        holder.subtitle.text = if (note.subtitle?.length ?: 0 < 10)
            note.subtitle
        else
            note.subtitle?.subSequence(0, 42).toString() + "..."


        holder.content.text = if (note.content?.length ?: 0 < 42)
            note.content
        else
            note.content?.subSequence(0, 42).toString() + "..."


        holder.title.text = note.title
        holder.subtitle.text = note.subtitle
        holder.content.text = note.content



        holder.view.setOnClickListener {
            val intent = Intent(context, NoteEditActivity::class.java)
            intent.putExtra(INTENT_EXTRA_NOTE_ID, modifiedNote)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
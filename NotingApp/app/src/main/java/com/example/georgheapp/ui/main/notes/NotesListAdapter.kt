package com.example.georgheapp.ui.main.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.georgheapp.R
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.NoteEditActivity
import com.example.georgheapp.data.NoteEditActivity.Companion.INTENT_EXTRA_NOTE_ID

class NotesListAdapter(val context: Context, val notes: ArrayList<Note>) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private var lastClick: Int? = null

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.noteTitle)
        val subtitle: TextView = view.findViewById(R.id.noteSubtitle)
        val content: TextView = view.findViewById(R.id.noteContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.title.text = note.title
        holder.subtitle.text = note.subtitle
        holder.content.text = note.content

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.getBoolean("color_particles", true)) {
            val color = when (note.tag) {
                Note.Tag.NOTE-> R.color.note
                Note.Tag.REMINDER-> R.color.reminder
            }

            //holder.image.setColorFilter(context.getColor(color))
        }

        holder.view.setOnClickListener {
            lastClick = position
            val intent = Intent(context, NoteEditActivity::class.java)
            intent.putExtra(INTENT_EXTRA_NOTE_ID, position)
            context.startActivity(intent)
        }
    }

    fun updateOnlyLastClick() {
        notifyItemChanged(lastClick ?: return)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
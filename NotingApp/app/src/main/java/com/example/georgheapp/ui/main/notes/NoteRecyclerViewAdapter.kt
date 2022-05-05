package com.example.georgheapp.ui.main.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.georgheapp.databinding.ItemListNoteBinding
import com.example.georgheapp.utils.toast

/**
 * Adapter with a notes list
 */
class NoteRecyclerViewAdapter(val context: Context, val notes: ArrayList<Note>) :
    RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    private var modifiedElement: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_note, parent, false)

        // Assignem el layout al ViewHolder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        val color = context.getColor(R.color.ColorAccent)

        // Set name
        holder.title.text = note.title
        holder.subtitle.text = note.subtitle
        holder.content.text = note.content

        // Set color
        // It converts the ID to the properly color and set it to the image
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.getBoolean("color_notes", true))
            holder.title.setTextColor(color)
        else
            holder.subtitle.setTextColor(color)

        // Set on item click listener
        holder.view.setOnClickListener {
            // Note Info
               context.toast(note.toString())

            modifiedElement = holder.absoluteAdapterPosition

            val intent = Intent(context, NoteEditActivity::class.java)
            intent.putExtra(INTENT_EXTRA_NOTE_ID, modifiedElement)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = notes.size

    fun notifyNoteUpdated() {
        modifiedElement?.let {
            notifyItemChanged(it)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListNoteBinding.bind(view)

        val title: TextView = binding.noteTitle
        val subtitle: TextView = binding.noteSubtitle
        val content: TextView = binding.noteContent
        //val image: ImageView = binding.particleImage
    }
}
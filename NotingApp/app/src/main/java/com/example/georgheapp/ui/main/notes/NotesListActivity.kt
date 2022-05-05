package com.example.georgheapp.ui.main.notes

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.georgheapp.R
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.Notes
import com.example.georgheapp.data.Persistence
import com.example.georgheapp.databinding.ActivityNotesListBinding
import com.google.android.material.chip.Chip
import org.w3c.dom.Text
import kotlin.reflect.safeCast


class NotesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesListBinding
    private var shownNotes = ArrayList<Note>()
    private val filters = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Read Notes file
        Persistence(this).readData()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val extraNotes = prefs.getStringSet("extra_Notes", HashSet())

        extraNotes?.forEach {
            Notes.add(Note(it, Note.Tag.NOTE, it, it))
        }

        val newArray = Notes.distinctBy { it.title }
        Notes.clear()
        Notes.addAll(newArray)


        // Implement views
        shownNotes = ArrayList(Notes)
        binding.notesList.adapter = NotesListAdapter(this, shownNotes)

        val NotesList = Notes.map { it.title } + Note.Tag.values().map { it.name }

        binding.searchBar.setAdapter(
            ArrayAdapter(
                this,
                R.layout.item_list_text,
                NotesList
            )
        )

        binding.searchBar.setOnItemClickListener { parent, _, position, _ ->
            // Obtenim el text de l'element seleccionat a la llista
            val selection = parent.getItemAtPosition(position).toString()

            val chip = Chip(this)
            chip.text = selection
            chip.chipIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_cancel_24)

            chip.setOnClickListener {
                filters.remove(selection)
                binding.chipsGroup.removeView(it)
                applyFiltersAndRefresh()
            }

            filters.add(selection)
            binding.chipsGroup.addView(chip)
            binding.searchBar.setText("")

            applyFiltersAndRefresh()
        }
    }
    
    override fun onResume() {
        super.onResume()

        NotesListAdapter::class.safeCast(binding.notesList.adapter)?.updateOnlyLastClick()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun  applyFiltersAndRefresh() {
        shownNotes.clear()

        filters.forEach { filter ->
            shownNotes.addAll(Notes.filter { it.title == filter })
            shownNotes.addAll(Notes.filter { it.subtitle == filter })
            shownNotes.addAll(Notes.filter { it.content == filter })
            shownNotes.addAll(Notes.filter { it.tag.name == filter })
        }

        if (shownNotes.isEmpty()) {
            shownNotes.addAll(Notes)
        }

        binding.notesList.adapter?.notifyDataSetChanged()
    }
}
package com.example.georgheapp.ui.main.notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.preference.PreferenceManager
import com.example.georgheapp.R
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.Notes
import com.example.georgheapp.data.Persistence
import com.example.georgheapp.databinding.ActivityNotesListBinding
import com.example.georgheapp.utils.toast
import com.google.android.material.chip.Chip
import kotlin.reflect.safeCast


class NotesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesListBinding
    private var shownNotes = ArrayList<Note>()
    private var filter = ""

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

        /*val NotesList = Notes.map { it.title } + Note.Tag.values().map { it.name } +
                Notes.map { it.subtitle } + Notes.map { it.content }*/

        /*binding.searchBar.setAdapter(
            ArrayAdapter(
                this,
                R.layout.item_list_text,
                NotesList
            )
        )*/

        binding.searchBar.addTextChangedListener {
            // Obtenim el text de l'element seleccionat a la llista

            filter = it.toString()

            applyFiltersAndRefresh()
        }

//        binding.searchBar.setOnItemClickListener { parent, _, position, _ ->
//            // Obtenim el text de l'element seleccionat a la llista
//            val selection = parent.getItemAtPosition(position).toString()
//
//            filter = selection
//            binding.searchBar.setText("")
//
//            applyFiltersAndRefresh()
//        }
    }

    override fun onResume() {
        super.onResume()

        NotesListAdapter::class.safeCast(binding.notesList.adapter)?.updateOnlyLastClick()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun applyFiltersAndRefresh() {
        shownNotes.clear()

        shownNotes.addAll(Notes.filter { it.title.contains(filter) })
        shownNotes.addAll(Notes.filter { it.subtitle?.contains(filter) == true })
        shownNotes.addAll(Notes.filter { it.content?.contains(filter) == true })
        shownNotes.addAll(Notes.filter { it.tag.name.contains(filter) })

//        if (shownNotes.isEmpty()) {
//            shownNotes.addAll(Notes)
//        }

        binding.notesList.adapter?.notifyDataSetChanged()
    }
}
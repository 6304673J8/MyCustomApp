package com.example.georgheapp.ui.main.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.georgheapp.R
import com.example.georgheapp.addNote.AddNoteActivity
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.Notes
import com.example.georgheapp.data.Persistence
import com.example.georgheapp.databinding.ActivityNotesListBinding
import com.example.georgheapp.databinding.FragmentNotesListBinding
import com.google.android.material.chip.Chip
import org.w3c.dom.Text
import kotlin.reflect.safeCast


class NotesListActivity : AppCompatActivity() {
    private lateinit var binding: FragmentNotesListBinding
    private var isDark : Boolean = false
    private var icon : Int = 0
    private var shownNotes = ArrayList<Note>()
    private val filters = ArrayList<String>()
    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hide Action Bar
        supportActionBar?.hide()

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
        //Searchbar Core Functionality
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

        //When Button Pressed Change Theme
        binding.toggleTheme.setOnClickListener { view ->
            //showSnackbar(view, R.string.settingsFragment)

            if (!isDark) {
                binding.toggleTheme.setImageResource(R.drawable.light_bulb_off_24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isDark = true

            } else if (isDark) {
                binding.toggleTheme.setImageResource(R.drawable.light_bulb_on_24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //isDark = false
            }
        }

        //When Button Pressed Share App
        binding.shareAppButton.setOnClickListener { view ->
            shareOnClick()
        }

        binding.fab.setOnClickListener {
            fabOnClick()
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

    private fun fabOnClick() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }

    private fun shareOnClick(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/drive/folders/1i3hFtyv7Szg8dOYVJ7L4NFUd9B3AnlK8?usp=sharing")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
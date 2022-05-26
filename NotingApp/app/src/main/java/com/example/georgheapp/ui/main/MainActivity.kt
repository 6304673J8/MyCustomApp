package com.example.georgheapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.preference.PreferenceManager

import com.example.georgheapp.R
import com.example.georgheapp.addNote.AddNoteActivity
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.Notes
import com.example.georgheapp.data.Persistence
import com.example.georgheapp.databinding.ActivityMainBinding
import com.example.georgheapp.ui.main.notes.NotesListActivity
import com.example.georgheapp.ui.main.notes.NotesListAdapter
import com.example.georgheapp.utils.toast
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlin.reflect.safeCast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isDark : Boolean = false
    private var shownNotes = ArrayList<Note>()
    private val filters = ArrayList<String>()
    private var filter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
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

        //When Button Pressed Do Something
        binding.settingsFragment.setOnClickListener { view ->
            //showSnackbar(view, R.string.settingsFragment)
            if (!isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isDark = true
            } else if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isDark = false
            }
        }

        binding.shareAppButton.setOnClickListener { view ->
            shareOnClick()
            /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission Obtained
                toast("I can use camera because I already have the permission :D")
                cameraLauncher.launch()
            } else {
                // No Permission Granted, Ask For It
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }*/
        }

        binding.notesListFragment.setOnClickListener { view ->
            startActivity(Intent(this, NotesListActivity::class.java))
        }

        binding.fab.setOnClickListener {
            fabOnClick()
        }

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


        // Get Selected Note In Order To Edit

        /*binding.searchBar.addTextChangedListener {
            // Obtenim el text de l'element seleccionat a la llista

            filter = it.toString()

            applyFiltersAndRefresh()
        }*/
    }

    override fun onResume() {
        super.onResume()

        NotesListAdapter::class.safeCast(binding.notesList.adapter)?.updateOnlyLastClick()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun applyFiltersAndRefresh() {
        shownNotes.clear()

        shownNotes.addAll(Notes.filter { it.title.contains(filter) })
        shownNotes.addAll(Notes.filter { it.subtitle?.contains(filter) == true })
        shownNotes.addAll(Notes.filter { it.content?.contains(filter) == true })
        shownNotes.addAll(Notes.filter { it.tag.name.contains(filter) })

//      if (shownNotes.isEmpty()) {
//          shownNotes.addAll(Notes)
//      }

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
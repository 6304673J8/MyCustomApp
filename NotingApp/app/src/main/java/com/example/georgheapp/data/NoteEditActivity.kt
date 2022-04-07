package com.example.georgheapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.georgheapp.R
import com.example.georgheapp.databinding.ActivityNoteEditBinding
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class NoteEditActivity : AppCompatActivity(){
    companion object{
        const val INTENT_EXTRA_NOTE_ID = "INTENT_EXTRA_NOTE_ID"
    }

    private lateinit var binding: ActivityNoteEditBinding
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            cancelAddNote()
        }

        val noteId = intent.extras?.getInt(INTENT_EXTRA_NOTE_ID)
            ?: return // leave it empty if no extra passed
        note = Notes[noteId]
        fillNotesInfo()
    }

    override fun onPause() {
        super.onPause()

        // Update class with user input data
        note?.title = binding.noteTitleInput.text.toString()
        note?.subtitle = binding.noteSubtitleInput.text.toString()
        note?.content = binding.noteContentInput.text.toString()
        //note?.date_time = binding.noteDateInput.text.toString()

        openFileOutput(Notes.NOTES_FILENAME, MODE_PRIVATE).use { io ->
            ObjectOutputStream(io).use {
                // Save the object on the file
                it.writeObject(Notes)
            }
        }
    }

    private fun fillNotesInfo() {
        note?.let { p ->
            binding.noteTitleInput.setText(p.title)
            binding.noteSubtitleInput.setText(p.subtitle)
            binding.noteContentInput.setText(p.content)
            //binding.noteDateInput.setText(p.date_time)
        }
    }

    private fun cancelAddNote() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_CANCELED, resultIntent)
        finish()
    }

    fun a() {
        // Manera 1
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sp.edit()
        //editor.putStringSet()
        editor.apply()


        // Manera 2
        val fos: FileOutputStream = openFileOutput("fileName", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(this)
        os.close()
        fos.close()
    }
}
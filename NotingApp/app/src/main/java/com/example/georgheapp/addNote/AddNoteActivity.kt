package com.example.georgheapp.addNote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.georgheapp.R
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.NoteEditActivity
import com.example.georgheapp.data.Notes
import com.example.georgheapp.databinding.ActivityAddNoteBinding
import com.example.georgheapp.utils.toast
import com.google.android.material.textfield.TextInputEditText
import java.io.ObjectOutputStream
import java.text.DateFormat
import java.util.*

const val NOTE_TITLE = "title"
const val NOTE_SUBTITLE = "subtitle"
const val NOTE_CONTENT = "content"
const val NOTE_DATE = "dd, MM, yy, hh:mm:ss"

class AddNoteActivity : AppCompatActivity() {
    companion object{
        const val INTENT_EXTRA_NOTE_ID = "INTENT_EXTRA_NOTE_ID"
    }

    lateinit var binding: ActivityAddNoteBinding
    private var note: Note? = null

    /*private lateinit var addNoteTitle: EditText
    private lateinit var addNoteSubTitle: EditText
    private lateinit var addNoteContent: EditText
    private lateinit var addNoteDate: TextView*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<ImageView>(R.id.goBack).setOnClickListener {
            cancelAddNote()
        }

        findViewById<ImageView>(R.id.saveNote).setOnClickListener {
            addNote()
            toast("Note Added")
        }

        val noteId = intent.extras?.getInt(INTENT_EXTRA_NOTE_ID)
            ?: return // leave it empty if no extra passed
        note = Notes[noteId]

        fillNotesInfo()
    }

    private fun fillNotesInfo() {
        note?.let { p ->
            binding.AddNoteTitle.setText(p.title)
            binding.AddNoteSubtitle.setText(p.subtitle)
            binding.AddNoteContent.setText(p.content)
            //binding.noteDateInput.setText(p.date_time)
        }
    }

    private fun cancelAddNote() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_CANCELED, resultIntent)
        finish()
    }

    private fun addNote() {
        //Added new test
        //setResult(Activity.RESULT_OK, resultIntent)
        note?.title = binding.AddNoteTitle.text.toString()
        note?.subtitle = binding.AddNoteSubtitle.text.toString()
        note?.content = binding.AddNoteContent.text.toString()
        openFileOutput(Notes.NOTES_FILENAME, MODE_PRIVATE).use { io ->
            ObjectOutputStream(io).use {
                // Save the object on the file
                it.writeObject(Notes)
            }
        }
    }
        /*val resultIntent = Intent()
        // In case the user doesn't input anything at all then the result is set to cancelled.
        if (addNoteTitle.text.isNullOrEmpty() && addNoteSubTitle.text.isNullOrEmpty()
            && addNoteContent.text.isNullOrEmpty()
        ) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else if (!addNoteTitle.text.isNullOrEmpty() || !addNoteSubTitle.text.isNullOrEmpty()
            || !addNoteContent.text.isNullOrEmpty()) {
            val title = addNoteTitle.text.toString()
            val subtitle = addNoteSubTitle.text.toString()
            val content = addNoteContent.text.toString()
            val date = addNoteDate.text.toString()

            resultIntent.putExtra(NOTE_TITLE, title)
            resultIntent.putExtra(NOTE_SUBTITLE, subtitle)
            resultIntent.putExtra(NOTE_CONTENT, content)
            resultIntent.putExtra(NOTE_DATE, date)
            setResult(Activity.RESULT_OK, resultIntent)
            openFileOutput(Notes.NOTES_FILENAME, MODE_PRIVATE).use { io ->
                ObjectOutputStream(io).use {
                    // Save the object on the file
                    it.writeObject(Notes)
                }
            }
        }*/
        //finish()
    //}
}
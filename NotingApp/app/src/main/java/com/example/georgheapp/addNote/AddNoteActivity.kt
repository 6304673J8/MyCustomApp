package com.example.georgheapp.addNote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.georgheapp.R
import com.example.georgheapp.databinding.ActivityAddNoteBinding
import com.google.android.material.textfield.TextInputEditText

const val NOTE_TITLE = "title"
const val NOTE_SUBTITLE = "subtitle"
const val NOTE_CONTENT = "content"
//const val NOTE_DATE = "dd, MM, yy, hh:mm:ss"
const val NOTE_DATE = "EEE, MMM d, ''yy"

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding

    private lateinit var addNoteTitle: EditText
    private lateinit var addNoteSubTitle: EditText
    private lateinit var addNoteContent: EditText
    //private lateinit var addNoteDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_note)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<ImageView>(R.id.saveNote).setOnClickListener {
            addNote()
        }
        findViewById<ImageView>(R.id.goBack).setOnClickListener {
            cancelAddNote()
        }
        addNoteTitle = findViewById(R.id.add_note_title)
        addNoteSubTitle = findViewById(R.id.add_note_subtitle)
        addNoteContent = findViewById(R.id.add_note_content)

        /*val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        textView.setText(currentDateTimeString)
        addNoteDate = findViewById(R.id.add_note_date)

        test -- val time = SimpleDateFormat("dd/MM/yy hh:mm:ss")
        currentDate = time.format(())

        */
    }

    private fun addNote() {
        val resultIntent = Intent()

        // In case the user doesn't input anything at all then the result is set to cancelled.
        if (addNoteTitle.text.isNullOrEmpty() && addNoteSubTitle.text.isNullOrEmpty()
            && addNoteContent.text.isNullOrEmpty()
        ) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val title = addNoteTitle.text.toString()
            val subtitle = addNoteSubTitle.text.toString()
            val content = addNoteContent.text.toString()
            //val date = addNoteDate.text.toString()

            resultIntent.putExtra(NOTE_TITLE, title)
            resultIntent.putExtra(NOTE_SUBTITLE, subtitle)
            resultIntent.putExtra(NOTE_CONTENT, content)
            //resultIntent.putExtra(NOTE_DATE, date)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    private fun cancelAddNote() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_CANCELED, resultIntent)
        finish()
    }
}
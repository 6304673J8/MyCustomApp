package com.example.georgheapp.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.example.georgheapp.R
import com.example.georgheapp.addNote.AddNoteActivity
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.Notes
import com.example.georgheapp.databinding.ActivityMainBinding
import com.example.georgheapp.ui.main.notes.NotesListActivity
import com.example.georgheapp.utils.toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesList : ArrayList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //When Button Pressed Do Something
        binding.settingsFragment.setOnClickListener { view ->
            showSnackbar(view, R.string.settingsFragment)
        }

        binding.shareAppButton.setOnClickListener { view ->
            shareOnClick()
        }

        binding.notesListFragment.setOnClickListener { view ->
            startActivity(Intent(this, NotesListActivity::class.java))
        }

        binding.fab.setOnClickListener {
            fabOnClick()
        }

        binding.standardBackgroundImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission Obtained
                toast("I can use camera because I already have the permission :D")
                cameraLauncher.launch()
            } else {
                // No Permission Granted, Ask For It
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun fabOnClick() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }

    private fun showSnackbar(view: View, message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
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

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmapThumbnail ->
            // Null If User Photo = False
            if (bitmapThumbnail != null)
                binding.standardBackgroundImage.setImageBitmap(bitmapThumbnail)
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                toast("Now I can use the camera :P")
                cameraLauncher.launch()
            } else {
                // Explain to the user that the feature is unavailable until permission given.
                toast("User has denied the permission :(")
            }
        }
}
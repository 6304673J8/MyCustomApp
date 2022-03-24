package com.example.georgheapp.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.example.georgheapp.R
import com.example.georgheapp.addNote.AddNoteActivity
import com.example.georgheapp.databinding.ActivityMainBinding
import com.example.georgheapp.databinding.FragmentNewNoteBinding
import com.example.georgheapp.utils.toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmapThumbnail ->
            // Serà null si l'usuari no fa cap foto
            if (bitmapThumbnail != null)
                binding.standardBackgroundImage.setImageBitmap(bitmapThumbnail)
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                toast("Now I can use camera :)")
                cameraLauncher.launch()
            } else {
                // Explain to the user that the feature is unavailable until permission given.
                toast("User has denied the permission :(")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //When Button Pressed Do Something
        binding.settingsFragment.setOnClickListener { view ->
            showSnackbar(view, R.string.settingsFragment)
        }

        binding.newNoteFragment.setOnClickListener { view ->
            showSnackbar(view, R.string.noteCreated)
        }

        binding.notesListFragment.setOnClickListener { view ->
            showSnackbar(view, R.string.notesList)
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
}
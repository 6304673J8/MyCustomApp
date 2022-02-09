package com.example.georgheapp.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Afegim el layout del main
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Afegim el layout del main
        //setContentView(R.layout.activity_main)

        //Initialize the bottom navigation view
        //create bottom navigation view object
        val bottomNavigationView = findViewById<BottomAppBar>(R.id.bottom_app_bar)
        //val navController = findNavController(R.id.nav_fragment)
        //bottomNavigationView.setupWithNavController(navController)
        val fab: View = findViewById(R.id.fab)
        binding.fab.setOnClickListener {
            fabOnClick()
        }
        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Do you want to know something?", Snackbar.LENGTH_LONG)
                .setAction("YES") {
                    toast("Maybe we live in a simulation")
                }.show()
        }*/
    }

    private fun fabOnClick() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }
}
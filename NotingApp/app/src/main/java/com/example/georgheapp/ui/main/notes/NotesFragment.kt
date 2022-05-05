package com.example.georgheapp.ui.main.notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.georgheapp.R
import com.example.georgheapp.data.Note
import com.example.georgheapp.data.Notes
import com.example.georgheapp.databinding.ActivityNotesListBinding
import com.google.android.material.chip.Chip
import kotlin.reflect.safeCast

class NotesFragment : Fragment() {

    private lateinit var binding: ActivityNotesListBinding
    private val filters = ArrayList<String>()
    private val notes = ArrayList<Note>().apply { addAll(Notes) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Init
        binding = ActivityNotesListBinding.inflate(inflater)
        val retView = binding.root
        val ctx = context ?: return retView.also { activity?.finish() }

        // Init adapter list
        binding.notesList.adapter = NoteRecyclerViewAdapter(ctx, notes)

        // Init adapter searchBar
        binding.searchBar.setOnItemClickListener { parent, _, position, _ ->

            // Obtenim el text de l'element seleccionat a la llista
            val selection = parent.getItemAtPosition(position).toString()

            // Dinamically Created Chip
            val chip = Chip(ctx)
            chip.text = selection
            chip.chipIcon = ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_cancel_24)

            // Si es clicka el chip, l'esborrem
            chip.setOnClickListener {
                filters.remove(chip.text)
                binding.chipsGroup.removeView(it)
                applyFiltersAndRefresh()
            }

            // Afegim el chip a la view, esborrem el text que ha escrit l'usuari i refresh
            filters.add(selection)
            binding.chipsGroup.addView(chip)
            binding.searchBar.setText("")

            applyFiltersAndRefresh()
        }

        return retView
    }

    override fun onResume() {
        super.onResume()

        // Notify some particle can have changed its name
        NoteRecyclerViewAdapter::class.safeCast(binding.notesList.adapter)?.notifyNoteUpdated()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData() {
        // En aquest cas, totes les dades de la llista poden haver canviat. Aquí s'hi arriba quan
        // es resetejen totes les dades
        val adapter = NoteRecyclerViewAdapter::class.safeCast(binding.notesList.adapter) ?: return
        adapter.notes.clear()
        adapter.notes.addAll(Notes)
        adapter.notifyDataSetChanged()
    }

    /**
     * Aquesta funció serveix per filtrar els elements de la llista que es veuran desprès d'haver
     * sel·leccionat algun filtre. En cas de no haver-hi cap filtre, es mostren tots els elemnts
     */
    @SuppressLint("NotifyDataSetChanged")
    fun applyFiltersAndRefresh() {
        // Important, primer netejem la llista d'elements a mostrar
        notes.clear()

        filters.forEach { filter ->

            // Mirem si el filtre coincideix amb el tag d'alguna nota
            /*val byTag = Notes.filter { it.tag.name == filter }
            if (byTag.isNotEmpty()) {
                notes.addAll(byTag)
            } else {

                // Mirem si el filtre coincideix amb el nom d'alguna partícula
                val byName = Notes.filter { it.title == filter }
                notes.addAll(byName)
            }*/
            val byTitle = Notes.filter { it.title == filter }
            notes.addAll(byTitle)
        }

        // En cas de no haver-hi cap "match", les mostrem totes
        if (notes.isEmpty())
            notes.addAll(Notes)

        // IMPORTANTÍSSIM! Hem de notificar a la vista que hem actualitzat els elements de la llista
        binding.notesList.adapter?.notifyDataSetChanged()
    }
}
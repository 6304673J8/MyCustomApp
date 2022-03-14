package com.example.georgheapp.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on notesLiveData and holds details about it. */
class DataSource(resources: Resources) {
    private val initialNoteList = noteList(resources)
    private val notesLiveData = MutableLiveData(initialNoteList)

    /* Adds note to liveData and posts value. */
    fun addNote(note: Note) {
        val currentList = notesLiveData.value
        if (currentList == null) {
            notesLiveData.postValue(listOf(note))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, note)
            notesLiveData.postValue(updatedList)
        }
    }

    /* Removes note from liveData and posts value. */
    fun removeNote(note: Note) {
        val currentList = notesLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(note)
            notesLiveData.postValue(updatedList)
        }
    }

    /* Returns note given an ID. */
    fun getNoteForId(id: Long): Note? {
        notesLiveData.value?.let { notes ->
            return notes.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getNoteList(): LiveData<List<Note>> {
        return notesLiveData
    }

    /* Returns a random note asset for notes that are added. */
    fun getRandomNoteImageAsset(): Int? {
        val randomNumber = (initialNoteList.indices).random()
        return initialNoteList[randomNumber].image
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
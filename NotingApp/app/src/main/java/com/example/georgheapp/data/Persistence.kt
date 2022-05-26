package com.example.georgheapp.data

import android.content.Context
import com.example.georgheapp.utils.toast
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Persistence (val context: Context) {

    fun saveData() {
        context.openFileOutput(Notes.NOTES_FILENAME, Context.MODE_PRIVATE).use { io ->
            ObjectOutputStream(io).use {
                it.writeObject(Notes)
            }
        }
    }

    fun readData() {
        val notes = try {
            context.openFileInput(Notes.NOTES_FILENAME).use { io ->
                ObjectInputStream(io).use {
                    it.readObject() as Notes
                }
            }
        } catch (e: IOException) {
            null
        }

        if (notes == null) {
            Notes.testerResetNotes()
        } else {
            Notes.clear()
            Notes.addAll(notes)
        }
    }
}
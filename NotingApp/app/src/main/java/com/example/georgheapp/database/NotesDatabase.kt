package com.example.georgheapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.georgheapp.dao.NoteDao
import com.example.georgheapp.data.Note

@Database(entities = [Note::class],version = 1,exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    companion object{
        var notesDatabase:NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase{
            if (notesDatabase == null){
                notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    "notes.db"
                ).build()
            }
            return notesDatabase as NotesDatabase
        }
    }
    abstract fun noteDao(): NoteDao
}
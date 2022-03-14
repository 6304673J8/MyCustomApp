package com.example.georgheapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Note class.
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY title")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE subtitleType = :subtitleType ORDER BY title")
    fun getNotesWithSubtitleText(subtitleType: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNote(noteId: String): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<Note>)
}
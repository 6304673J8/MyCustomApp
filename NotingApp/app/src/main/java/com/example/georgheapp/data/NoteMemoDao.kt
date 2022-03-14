package com.example.georgheapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [NoteMemo] class.
 */
@Dao
interface NoteMemoDao {
    @Query("SELECT * FROM note_memos")
    fun getGardenPlantings(): Flow<List<NoteMemo>>

    @Query("SELECT EXISTS(SELECT 1 FROM note_memos WHERE note_id = :noteId LIMIT 1)")
    fun isNoted(noteId: String): Flow<Boolean>

    /**
     * This query will tell Room to query both the [Note] and [NoteMemo] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM notes WHERE id IN (SELECT DISTINCT(note_id) FROM note_memos)")
    fun getNotedMemos(): Flow<List<NoteAndNoteMemos>>

    @Insert
    suspend fun insertNoteMemo(noteMemo: NoteMemo): Long

    @Delete
    suspend fun deleteNoteMemo(noteMemo: NoteMemo)
}

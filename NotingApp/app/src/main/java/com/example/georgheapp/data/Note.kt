package com.example.georgheapp.data

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey @ColumnInfo(name = "id") val noteId: String,
    val id: Long,
    val title: String,
    val subtitle: String,
    val subtitleType: String,
    val content: String,
    val updateInterval: Int = 7,
    @DrawableRes
    val image: Int?
    ){

    /**
     * Determines if the note should be updated.  Returns true if [since]'s date > date of NoteMemo
     * Interval; false otherwise.
     */
    fun shouldBeUpdated(since: Calendar, lastUpdatingDate: Calendar) =
        since > lastUpdatingDate.apply { add(Calendar.DAY_OF_YEAR, updateInterval) }

    override fun toString() = title
}

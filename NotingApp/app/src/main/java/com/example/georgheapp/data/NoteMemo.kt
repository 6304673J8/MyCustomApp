package com.example.georgheapp.data

import androidx.room.*
import java.util.*

@Entity(
    tableName = "note_memos",
    foreignKeys = [
        ForeignKey(entity = Note::class, parentColumns = ["id"], childColumns = ["note_id"])
    ],
    indices = [Index("note_id")]
)
data class NoteMemo (
    @ColumnInfo(name = "note_id") val noteId: String,
    @ColumnInfo(name = "note_date") val noteDate: Calendar = Calendar.getInstance(),

    @ColumnInfo(name = "last_update_date")
    val lastUpdateDate: Calendar = Calendar.getInstance()

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var noteMemoId: Long = 0
}
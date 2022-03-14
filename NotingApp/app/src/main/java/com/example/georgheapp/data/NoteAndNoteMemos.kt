package com.example.georgheapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class NoteAndNoteMemos (
    @Embedded
    val note: Note,

    @Relation(parentColumn = "id", entityColumn = "note_id")
    val noteMemos: List<NoteMemo> = emptyList()
        )
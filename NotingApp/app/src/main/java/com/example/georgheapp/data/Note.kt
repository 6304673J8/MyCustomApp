package com.example.georgheapp.data

import java.io.Serializable

data class Note(
    var title: String,
    var tag: Tag,
    var subtitle: String? = null,
    var content: String? = null,
    var date_time: Long? = null
): Serializable {
    enum class Tag {
        NOTE, REMINDER
    }
}
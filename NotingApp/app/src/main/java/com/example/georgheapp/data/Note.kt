package com.example.georgheapp.data

import java.io.Serializable

data class Note(
    var title: String,
    var tag: Tag,
    var subtitle: String,
    var content: String
    //var date_time: String
): Serializable {

    enum class Tag {
        NOTE, REMINDER
    }
}
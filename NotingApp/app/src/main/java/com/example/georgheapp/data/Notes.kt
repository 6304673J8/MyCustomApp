package com.example.georgheapp.data

object Notes : ArrayList<Note>() {
    const val NOTES_FILENAME = "notesInternData.dat"

    fun testerResetNotes() {
        clear()

        add(Note("Test0", Note.Tag.NOTE,"zeroed", "-1sggsgsgvbvxbcnfnfnnfngfn/3"))
        add(Note("Test0arm", Note.Tag.NOTE,"armed", "-1/gfwseggsggs3"))
        add(Note("Test0p", Note.Tag.NOTE,"pi", "-1qegfg/3"))

        add(Note("Test0wn", Note.Tag.REMINDER,"Oop", "-1dasdawq/3"))
        add(Note("Test0range", Note.Tag.REMINDER,"Deranged", "-1sad/3"))
        add(Note("Test0ttom", Note.Tag.NOTE,"Bottom", "-1sdasdadad/3"))

    }
}
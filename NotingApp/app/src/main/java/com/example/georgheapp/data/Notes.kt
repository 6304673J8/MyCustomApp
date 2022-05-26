package com.example.georgheapp.data

object Notes : ArrayList<Note>() {
    const val NOTES_FILENAME = "notesMainlData.dat"

    fun testerResetNotes() {
        clear()

        add(Note("Dia 1: ", Note.Tag.NOTE,"Unreal", "Market Place Goes Brr"))
        add(Note("Dia 2:", Note.Tag.NOTE,"Godot", "VR Goes Brr"))
        add(Note("Dia 3:", Note.Tag.NOTE,"Cobol", "Capitalism Goes Brr"))
        add(Note("Dia 4:", Note.Tag.NOTE,"Unity", "Poly Art Goes Brr"))
        add(Note("Dia 5:", Note.Tag.NOTE,"RPG Maker", "Pixel Art Goes Brr"))
        add(Note("Dia 6:", Note.Tag.NOTE,"CPP Class", "Game Industry Goes Brr"))
        add(Note("Dia 7:", Note.Tag.NOTE,"Kotlin Class", "App Development Goes Brr"))

    }
}
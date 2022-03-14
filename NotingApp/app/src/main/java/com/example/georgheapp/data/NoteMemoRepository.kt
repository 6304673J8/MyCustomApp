package com.example.georgheapp.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteMemoRepository @Inject constructor(
    private val noteMemoDao: NoteMemoDao
) {

    suspend fun createNoteMemo(noteId: String) {
        val noteMemo = NoteMemo(noteId)
        noteMemoDao.insertNoteMemo(noteMemo)
    }

    suspend fun removeNoteMemo(noteMemo: NoteMemo) {
        noteMemoDao.deleteNoteMemo(noteMemo)
    }

    fun isPlanted(plantId: String) =
        noteMemoDao.isNoted(plantId)

    fun getNotedMemos() = noteMemoDao.getNotedMemos()
}
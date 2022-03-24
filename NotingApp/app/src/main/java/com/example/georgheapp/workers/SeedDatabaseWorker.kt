package com.example.georgheapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.georgheapp.data.Note
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//JSON Intepreter, TODO Database
class SeedDatabaseWorker (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        try{
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null){
               applicationContext.assets.open(filename).use { inputStream ->
                   JsonReader(inputStream.reader()).use { jsonReader ->
                       val noteType = object : TypeToken<List<Note>>() {}.type
                       //Gson is used to serialize and deserialize Java objects to JSON
                       val noteList: List<Note> = Gson().fromJson(jsonReader, noteType)

                       /**
                        * After Implementing AppDatabase The Notes Will Be Inserted
                        */
                       // val database = AppDatabase.getInstance(applicationContext)
                       // database.noteDao().insertAll(noteList)

                       Result.success()
                   }
               }
            } else {
                Log.e(TAG, "@string/error_seed_db_name")
                Result.failure()
            }
        } catch (ex: Exception){
            Log.e(TAG, "@string/error_seed_db", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "NOTE_DATA_FILENAME"
    }
}
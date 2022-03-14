package com.example.georgheapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.georgheapp.data.NoteAndNoteMemos
import com.example.georgheapp.data.NoteMemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteMemoListViewModel @Inject internal constructor(
    noteMemoRepository: NoteMemoRepository
) : ViewModel() {
    val noteAndNoteMemos: LiveData<List<NoteAndNoteMemos>> =
        noteMemoRepository.getNotedMemos().asLiveData()

}
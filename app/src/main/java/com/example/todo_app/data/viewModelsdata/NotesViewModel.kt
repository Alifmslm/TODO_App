package com.example.todo_app.data.viewModelsdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.todo_app.data.NoteDataBase
import com.example.todo_app.data.model.NoteData
import com.example.todo_app.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDataBase.getDataBase(application).noteDao()
    private val repository: NoteRepository
    val getAllData: LiveData<List<NoteData>>
    val shortByHighPriority: LiveData<List<NoteData>>
    val shortByLowPriority: LiveData<List<NoteData>>

    init {
        repository = NoteRepository(noteDao)
        getAllData = repository.getAllData
        shortByHighPriority = repository.sortByHighPriority
        shortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(noteData)
        }
    }

    fun delateData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(noteData)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>> {
        return repository.searchData(searchQuery)
    }

}
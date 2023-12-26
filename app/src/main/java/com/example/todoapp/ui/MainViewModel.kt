package com.example.todoapp.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.repository.Repository
import com.example.todoapp.data.room.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    context: Context
): ViewModel() {
    private var showAll = true
    private val repository: Repository = Repository(TodoDatabase.create(context))


    private var _numberOfDone = MutableStateFlow(0)
    var numberOfDone = _numberOfDone.asStateFlow()
    private var lastId = -1

    private val _list = MutableStateFlow<List<Task>>(listOf())
    val list = _list.asStateFlow()


    fun getLastId(): Int {
        for (i in list.value) lastId = maxOf(lastId, i.id)
        lastId++
        return lastId
    }

    init {
        getList(false)
    }

    fun getList(mode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            showAll = !mode
            when (showAll) {
                true -> _list.value = repository.getData()
                false -> _list.value = repository.getDoneData()
            }
            _numberOfDone.value = repository.getNumDone()
        }
    }

    fun addItem(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(task)
            getList(false)
        }

    }

    fun updateItem(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(task)
            getList(false)
        }

    }

    fun deleteItem(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(task)
            getList(false)
        }

    }

}
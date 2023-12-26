package com.example.todoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.repository.Repository

class MainViewModel: ViewModel() {
    private val repository = Repository()
    private var showAll = true

    val numberOfDone: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val list: MutableLiveData<List<Task>> by lazy {
        MutableLiveData<List<Task>>()
    }

    private var lastId: Int = 0

    fun getLastId() = lastId

    init {
        list.value = repository.getData()
        numberOfDone.value = repository.getNumDone()
    }

    fun getList(mode: Boolean) {
        showAll = !mode

        when (showAll) {
            true -> list.postValue(repository.getData())
            false -> list.postValue(repository.getData().filter { it.done })
        }

        numberOfDone.postValue(repository.getNumDone())
        lastId = repository.getLastId()
    }

    fun addItem(task: Task) {
        repository.add(task)
        getList(false)
    }

    fun updateItem(task: Task) {
        repository.update(task)
        getList(false)
    }

    fun deleteItem(task: Task) {
        repository.delete(task)
        getList(false)
    }

}
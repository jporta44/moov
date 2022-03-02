package com.moov.moovapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moov.moovapp.model.User

class UserViewModel : ViewModel() {
    private val users = MutableLiveData<List<User>>()
    private val selected = MutableLiveData<User>()

    init {
        loadUsers()
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun select(user: User) {
        selected.value = user
    }

    private fun loadUsers() {
        users.value = listOf(User(1, "Jhon", "Dow"), User(2, "Amir", "Khan"))
    }
}
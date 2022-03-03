package com.moov.moovapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moov.moovapp.model.User
import com.moov.moovapp.repo.UserApi
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val users = MutableLiveData<List<User>>()
    val selected = MutableLiveData<User>()

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
        viewModelScope.launch {
            users.value = UserApi.getUsers(1)
        }

        //users.value = listOf(User(1, "Jhon", "Dow"), User(2, "Amir", "Khan"))
    }
}
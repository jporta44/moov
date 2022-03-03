package com.moov.moovapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moov.moovapp.api.UserPagingSource
import com.moov.moovapp.model.User

class UserViewModel : ViewModel() {
    val selected = MutableLiveData<User>()

    fun select(user: User) {
        selected.value = user
    }

    val flow = Pager(
        PagingConfig(pageSize = 1)
    ) {
        UserPagingSource()
    }.flow.cachedIn(viewModelScope)
}
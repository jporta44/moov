package com.moov.moovapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.moov.moovapp.api.UserPagingSource
import com.moov.moovapp.model.User

/**
 * ViewModel for User rendering.
 * [selected] is used to share info between fragments (selected user from list).
 * [flow] is a stream of [PagingData] from [UserPagingSource].
 * [Pager] calls [UserPagingSource.load] with appropriate [PagingSource.LoadParams]
 * and receives [PagingSource.LoadResult]
 */

class UserViewModel : ViewModel() {
    val selected = MutableLiveData<User>()

    fun select(user: User) {
        selected.value = user
    }

    val flow = Pager(
        // Using prefetchDistance = 1 to prevent loading all pages at once
        PagingConfig(pageSize = 6, prefetchDistance = 1)
    ) {
        UserPagingSource()
    }.flow.cachedIn(viewModelScope)
}
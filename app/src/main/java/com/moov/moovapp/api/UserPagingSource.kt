package com.moov.moovapp.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moov.moovapp.model.User

class UserPagingSource : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = UserApi.getUsers(nextPageNumber)
            return LoadResult.Page(
                data = response.users,
                prevKey = null, // Only paging forward.
                nextKey = if (response.page < response.totalPages) response.page.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

}
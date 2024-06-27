package com.prafull.recipesearchapp.utils

sealed interface NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>
    data class Error<T>(val message: String) : NetworkResponse<T>
    data object Loading : NetworkResponse<Nothing>
}
package com.prafull.recipesearchapp.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

class AuthViewModel : ViewModel(), KoinComponent {


    var loading by mutableStateOf(false)
}
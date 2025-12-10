package com.example.randomuser.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.ApiResponse
import com.example.randomuser.domain.User
import com.example.randomuser.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomUserViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    val users = userRepository.getUsers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser = _selectedUser.asStateFlow()

    var requestedUser by mutableStateOf<ApiResponse<User>>(ApiResponse.Loading())
        private set

    fun requestRandomUser(
        gender: String,
        nationality: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        requestedUser = ApiResponse.Loading()
        val apiResponse = userRepository.requestRandomUser(gender, nationality)
        requestedUser = apiResponse
        if (apiResponse is ApiResponse.Success) {
            _selectedUser.value = apiResponse.data
            userRepository.insertUser(apiResponse.data)
        }
    }

    fun onUserSelected(user: User) {
        _selectedUser.value = user
    }
}

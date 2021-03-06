package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Blog
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val blogsLiveData: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val getBlogsLiveData: LiveData<DataState<List<Blog>>>
        get() = blogsLiveData

    fun getBlogsFromServer() {
        viewModelScope.launch {
            mainRepository.getBlogs().onEach { blogs ->
                blogsLiveData.value = blogs
            }.launchIn(viewModelScope)
        }
    }

}
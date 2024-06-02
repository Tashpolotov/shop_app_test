package com.example.core_urils.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_urils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<Resource<List<T>>>.listCollectData(_state: MutableStateFlow<Resource<List<T>>>) {


        viewModelScope.launch(Dispatchers.IO) {
            this@listCollectData.collect { res ->
                when (res) {
                    is Resource.Error -> {
                        if (res.message != null) {
                            _state.value = Resource.Error(res.message!!)
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        if (res.data != null) {
                            _state.value = Resource.Success(res.data!!)
                        }
                    }
                    is Resource.Empty -> TODO()
                    else -> {}
                }
            }
        }
    }

    protected fun <T> Flow<Resource<T>>.collectData(_state: MutableStateFlow<Resource<T>>) {
        viewModelScope.launch(Dispatchers.IO) { this@collectData.collect { res ->
            Log.d("BaseViewModel", "collectData: $res")
                when (res) {
                    is Resource.Error -> {
                        _state.value = Resource.Error(res.message)
                    }
                    is Resource.Loading -> {
                        _state.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        if (res.data != null) {
                            _state.value = Resource.Success(res.data!!)
                        }
                    }
                    is Resource.Empty -> {

                    }
                }
            }
        }
    }
}
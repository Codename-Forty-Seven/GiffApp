package com.thegiff.giffapp.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thegiff.giffapp.utils.Constants.internet_view_model_tag
import com.thegiff.giffapp.utils.isOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _isConnectToInternet = MutableLiveData<Boolean>()
    val isConnectToInternet: LiveData<Boolean> get() = _isConnectToInternet

    init {
        Log.d(internet_view_model_tag, "init()")
        viewModelScope.launch {
            while (isActive) {
                _isConnectToInternet.postValue(isOnline(context))
                delay(2000)
            }
        }
    }
}
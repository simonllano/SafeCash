package com.simonllano.safecash.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _errorMSG: MutableLiveData<String?> = MutableLiveData()
    val errorMSG: LiveData<String?> = _errorMSG

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

}
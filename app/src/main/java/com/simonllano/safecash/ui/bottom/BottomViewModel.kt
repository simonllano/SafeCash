package com.simonllano.safecash.ui.bottom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.UserRepository
import kotlinx.coroutines.launch

class BottomViewModel : ViewModel() {


    private val userRepository = UserRepository()

    private val _errorMSG : MutableLiveData<String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG

    private val _userLoggedIn : MutableLiveData<Boolean> = MutableLiveData()
    val userLoggedIn : LiveData<Boolean> = _userLoggedIn
    fun verifyUser() {
        viewModelScope.launch {
            val result = userRepository.verifyUser()
            result.let{resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success ->{
                        if (result.data==false)
                        _userLoggedIn.postValue(true)
                    }
                    is ResourceRemote.Error ->{
                        var msg = result.message
                        when(msg){
                            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg = "Revise su conexion a internet"
                        }
                        _errorMSG.postValue(msg)
                    }
                    else -> {
                        //don't use
                    }
                }
            }
        }
    }
}
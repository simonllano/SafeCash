package com.simonllano.safecash.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.UserRepository
import emailValidator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _errorMSG : MutableLiveData<String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG

    private val _registerSuccess : MutableLiveData <Boolean> = MutableLiveData()
    val registerSuccess : LiveData<Boolean> = _registerSuccess

    fun validateFields(email: String, password: String) {
        if( email.isEmpty() || password.isEmpty()) {
            _errorMSG.value = "Debe digitar todos los campos"
        }
        else {
            if (password.length < 6){
                _errorMSG.value = "La contraseña debe tener al menos 6 digitos"
            }
            else{
                if(!emailValidator(email)){
                    _errorMSG.value = "Direción de correo no cumple el formato"
                }
                else{
                    viewModelScope.launch {
                        val result = userRepository.loginUser(email, password)
                        result.let{resourceRemote ->
                            when(resourceRemote){
                                is ResourceRemote.Success ->{
                                    _errorMSG.postValue("Bienvenido")
                                    _registerSuccess.postValue(true)
                                }
                                is ResourceRemote.Error ->{
                                    var msg = result.message
                                    when(msg){
                                        "The email address is already in use by another account." -> msg = "Ya existe una cuenta con esta direcion de correo electronico"
                                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg = "Revise su conexion a internet"
                                        "An internal error has occurred. [ INVALID_LOGIN_CREDENTIALS ]" -> msg = "Correo o contraseña invalido"
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

        }
    }
}
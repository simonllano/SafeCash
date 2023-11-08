package com.simonllano.safecash.ui.register
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.UserRepository
import com.simonllano.safecash.data.model.User
import emailValidator
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val userRepository = UserRepository()
    private val _errorMSG : MutableLiveData <String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG

    private val _registerSuccess : MutableLiveData <Boolean> = MutableLiveData()
    val registerSuccess : LiveData<Boolean> = _registerSuccess

    fun validateFields(
        name: String,
        lastname: String,
        user: String,
        email: String,
        password: String,
        reppassword: String,
        age: String,
        genre: String
    ) {
        if(name.isEmpty() || lastname.isEmpty() || user.isEmpty()||email.isEmpty() || password.isEmpty() || reppassword.isEmpty()){
            _errorMSG.value = "Debe digitar todos los campos"
        }

        else{
            if(password != reppassword){
            _errorMSG.value = "Las contraseñas no son iguales"}
            else{
                if (password.length < 6){
                    _errorMSG.value = "La contraseña debe tener al menos 6 digitos"
                }
                else{
                    if(!emailValidator(email)){
                        _errorMSG.value = "Direción de correo no cumple el formato"
                    }
                    else{
                        viewModelScope.launch{
                            val result = userRepository.registerUser(email, password)
                            result.let{resourceRemote ->
                                when(resourceRemote){
                                    is ResourceRemote.Success ->{
                                        val uid = result.data
                                        uid?.let { Log.d("uid user", it) }
                                        val user = User(uid,name,lastname,email,user,age,genre)
                                        createUser(user)
                                    }
                                    is ResourceRemote.Error ->{
                                        var msg = result.message
                                        when(msg){
                                            "The email address is already in use by another account." -> msg = "Ya existe una cuenta con esta direcion de correo electronico"
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
            }

        }
    }

    private fun createUser(user: User) {
        viewModelScope.launch{
            var result = userRepository.createUser(user)
            result.let{ resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success->{
                        _registerSuccess.postValue(true)
                        _errorMSG.postValue("Usuario creado exitosamente")
                    }
                    is ResourceRemote.Error-> {
                        var msg= result.message
                        _errorMSG.postValue(msg)
                    }
                    else ->{
                        // Don't use
                    }
                }

            }
        }

    }
}
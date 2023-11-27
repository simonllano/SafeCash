package com.simonllano.safecash.ui.perfil

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.ResourceRemote
import kotlinx.coroutines.launch
import com.simonllano.safecash.data.UserRepository
import com.simonllano.safecash.data.model.User


class PerfilViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _userData: MutableLiveData<User?> = MutableLiveData()
    val userData: LiveData<User?> = _userData

    private val _userLoggedOut : MutableLiveData<Boolean> = MutableLiveData()
    val userLoggedOut : LiveData<Boolean> = _userLoggedOut
    fun loadCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.uid?.let { uid ->
            Log.d("PerfilViewModel", "UID del usuario: $uid")
            loadUser(uid)
        } ?: run {
            _errorMsg.postValue("El usuario actual no tiene un UID válido.")
        }
    }

    private fun loadUser(uid: String) {
        viewModelScope.launch {
            val result = userRepository.loadUser(uid)
            result.let { resourceRemote ->
                when (resourceRemote) {
                    is ResourceRemote.Success -> {
                        result.data?.let { user ->
                            Log.d("PerfilViewModel", "Datos del usuario después de la actualización: $user")
                            _userData.postValue(user)
                        }
                    }
                    is ResourceRemote.Error -> {
                        val msg = result.message
                        _errorMsg.postValue(msg)
                        Log.e("PerfilViewModel", "Error al cargar usuario después de la actualización: $msg")
                    }
                    else -> {
                        Log.e("PerfilViewModel", "Error inesperado al cargar usuario después de la actualización")
                    }
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val result = userRepository.signOut()
            result.let { resourceRemote ->
                when (resourceRemote) {
                    is ResourceRemote.Success -> {
                        if (result.data == true)
                            _userLoggedOut.postValue(true)
                    }

                    is ResourceRemote.Error -> {
                        var msg = result.message
                        when (msg) {
                            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg =
                                "Revise su conexion a internet"
                        }
                        _errorMsg.postValue(msg)
                    }

                    else -> {
                        //don't use
                    }
                }
            }
        }
    }
}
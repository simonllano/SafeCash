package com.simonllano.safecash.ui.income

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.IncomeRepository
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.model.Income
import com.simonllano.safecash.data.model.User
import kotlinx.coroutines.launch

class IncomeViewModel : ViewModel() {

    val incomeRepository = IncomeRepository()

    private val _errorMSG : MutableLiveData <String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG

    private val _createIncomeSuccess : MutableLiveData <Boolean> = MutableLiveData()
    val createIncomeSuccess : LiveData<Boolean> = _createIncomeSuccess
    fun validateFields(value: Double, note: String, date: String, tipo: String) {
        if(value.isNaN() || note.isEmpty() || date.isEmpty() ){
            _errorMSG.value = "Debe digitar todos los campos"
        }
        else{
            val income = Income(valor = value, note = note, tipo = tipo, date = date)
            viewModelScope.launch {
                val result = incomeRepository.createIncome(income)
                result.let{resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success ->{
                            _errorMSG.postValue("Datos guardados con exito")
                            _createIncomeSuccess.postValue(true)
                        }
                        is ResourceRemote.Error ->{
                            var msg = result.message

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
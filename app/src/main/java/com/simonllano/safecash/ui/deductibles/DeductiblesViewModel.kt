package com.simonllano.safecash.ui.deductibles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.DeductiblesRepository
import com.simonllano.safecash.data.IncomeRepository
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.model.Deductibles
import com.simonllano.safecash.data.model.Income
import kotlinx.coroutines.launch

class DeductiblesViewModel : ViewModel() {

    val deductiblesRepository = DeductiblesRepository()

    private val _errorMSG: MutableLiveData<String?> = MutableLiveData()
    val errorMSG: LiveData<String?> = _errorMSG

    private val _createDeductiblesSuccess : MutableLiveData <Boolean> = MutableLiveData()
    val createDeductiblesSuccess : LiveData<Boolean> = _createDeductiblesSuccess

    fun validateFields(value: Double, type: String, date: String) {
        if (value.isNaN() || type.isEmpty() || date.isEmpty()) {
            _errorMSG.value = "Debe digitar todos los campos"
        } else {
            val deductibles = Deductibles(value = value, name = type, date = date)
            viewModelScope.launch{

                val result = deductiblesRepository.createDeductible(deductibles)
                result.let{resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success ->{
                            _errorMSG.postValue("Datos guardados con exito")
                            _createDeductiblesSuccess.postValue(true)

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


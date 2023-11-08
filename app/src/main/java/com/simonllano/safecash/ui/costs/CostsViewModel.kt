package com.simonllano.safecash.ui.costs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonllano.safecash.data.CostsRepository
import com.simonllano.safecash.data.IncomeRepository
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.model.Costs
import com.simonllano.safecash.data.model.Income
import kotlinx.coroutines.launch

class CostsViewModel : ViewModel() {
    val costsRepository = CostsRepository()

    private val _errorMSG : MutableLiveData <String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG

    private val _createCostsSuccess : MutableLiveData <Boolean> = MutableLiveData()
    val createCostsSuccess : LiveData<Boolean> = _createCostsSuccess
    fun validateFields(value: Double, name: String, date: String, category: String) {
        if(value.isNaN() || name.isEmpty() || date.isEmpty() || category=="Seleciona"){
            _errorMSG.value = "Debe digitar todos los campos"
        }
        else{
            val costs = Costs(value = value, name = name, date = date, category = category)
            viewModelScope.launch {
                val result = costsRepository.createCosts(costs)
                result.let{resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success ->{
                            _errorMSG.postValue("Datos guardados con exito")
                            _createCostsSuccess.postValue(true)
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
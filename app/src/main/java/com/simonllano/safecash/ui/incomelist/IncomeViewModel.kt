package com.simonllano.safecash.ui.incomelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.simonllano.safecash.data.IncomeRepository
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.model.Income
import kotlinx.coroutines.launch

class IncomeViewModel : ViewModel() {

    val incomeRepository = IncomeRepository()

    private  val incomeListLocal = mutableListOf<Income?>()

    private val _incomeList : MutableLiveData<MutableList<Income?>> = MutableLiveData()
    val incomeList : LiveData<MutableList<Income?>> = _incomeList

    private val _errorMSG : MutableLiveData <String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG
    fun loadIncome() {
        incomeListLocal.clear()
        viewModelScope.launch{
            val result = incomeRepository.loadDatos()
            result.let{resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success ->{
                        result.data?.documents?.forEach{document ->
                            val income = document.toObject<Income>()
                            incomeListLocal.add(income)
                        }
                        _incomeList.postValue(incomeListLocal)

                    }


                    is ResourceRemote.Error ->{
                        var msg = result.message

                        _errorMSG.postValue(msg)
                    }
                    else -> { //Don't use}
                    }
                }  }
        }
    }


}
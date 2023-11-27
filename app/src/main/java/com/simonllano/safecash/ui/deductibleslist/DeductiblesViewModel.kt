package com.simonllano.safecash.ui.deductibleslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.simonllano.safecash.data.DeductiblesRepository
import com.simonllano.safecash.data.IncomeRepository
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.model.Deductibles
import com.simonllano.safecash.data.model.Income
import kotlinx.coroutines.launch

class DeductiblesViewModel : ViewModel() {

    val deductiblesRepository = DeductiblesRepository()

    private  val deductiblesListLocal = mutableListOf<Deductibles?>()

    private val _deductiblesList : MutableLiveData<MutableList<Deductibles?>> = MutableLiveData()
    val deductiblesList : LiveData<MutableList<Deductibles?>> = _deductiblesList

    private val _errorMSG : MutableLiveData <String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG
    fun loadDeductibles() {
        deductiblesListLocal.clear()
        viewModelScope.launch{
            val result = deductiblesRepository.loadDatos()
            result.let{resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success ->{
                        result.data?.documents?.forEach{document ->
                            val deductibles = document.toObject<Deductibles>()
                            deductiblesListLocal.add(deductibles)
                        }
                        _deductiblesList.postValue(deductiblesListLocal)

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
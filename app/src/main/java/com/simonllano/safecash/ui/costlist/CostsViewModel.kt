package com.simonllano.safecash.ui.costlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.simonllano.safecash.data.CostsRepository
import com.simonllano.safecash.data.ResourceRemote
import com.simonllano.safecash.data.model.Costs
import kotlinx.coroutines.launch

class CostsViewModel : ViewModel() {

    val costsRepository = CostsRepository()

    private  val costsListLocal = mutableListOf<Costs?>()

    private val _costsList : MutableLiveData<MutableList<Costs?>> = MutableLiveData()
    val costsList : LiveData<MutableList<Costs?>> = _costsList

    private val _errorMSG : MutableLiveData <String?> = MutableLiveData()
    val errorMSG : LiveData<String?> = _errorMSG
    fun loadCosts() {
        costsListLocal.clear()
        viewModelScope.launch{
            val result = costsRepository.loadDatos()
            result.let{resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success ->{
                        result.data?.documents?.forEach{document ->
                            val costs = document.toObject<Costs>()
                            costsListLocal.add(costs)
                        }
                        _costsList.postValue(costsListLocal)

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
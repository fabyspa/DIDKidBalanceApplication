package it.polito.ic2020.did_kidbalanceapplication.BHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BHomeViewModel: ViewModel() {
    private val _actionDayDone = MutableLiveData<Boolean>()
    val actionDayDone:LiveData <Boolean>
    get(){
        return _actionDayDone
    }
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

    fun onActionDayDone(){
        _actionDayDone.value= true
    }
}

package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {
    //rivate val _items= mutableListOf<NewChildUser>()
    private val _childUser = MutableLiveData<NewChildUser>()
    val childUser : LiveData<NewChildUser>
        get() {
            return _childUser
        }
    lateinit var sp: SharedPreferences

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "HomeViewModel destroyed")
    }
    /*fun addUser(name: String){
        val newUser= NewChildUser(name)
        //_items.add(newUser)
        _childUser.value=newUser
        Log.i("HomeViewModels", "${_childUser.value}")

    }*/



}
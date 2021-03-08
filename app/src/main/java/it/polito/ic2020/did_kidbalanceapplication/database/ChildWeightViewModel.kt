package it.polito.ic2020.did_kidbalanceapplication.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChildWeightViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ChildWeight>>

    //val getWeightById : LiveData<List<Float>>
    private val repository: ChildWeightRepository

    init {
        val childWeightDao = ChildWeightDatabase.getInstance(application).childDataBaseDao()
        repository = ChildWeightRepository(childWeightDao)
        readAllData = repository.readAllData
        //getWeightById = repository.getWeightById
    }

    fun addChildWeight(child: ChildWeight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addChild(child)

        }
    }

    fun addGameWeight(gameWeight: GameWeight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGameWeight(gameWeight)
        }
    }
    fun getImgProfile(child: String): Int {
       var img:Int =0
         viewModelScope.launch (Dispatchers.IO){
             withContext(Dispatchers.Main) {
                 img= repository.getImgProfile(child)
                 println("viewModel: $img")
             }

        }
        return img
    }

}
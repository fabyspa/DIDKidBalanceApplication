package it.polito.ic2020.did_kidbalanceapplication.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChildWeightViewModel(application: Application): AndroidViewModel(application) {

    val readAllData : LiveData<List<ChildWeight>>
    val getWeightById : LiveData<List<Float>>
    private val repository: ChildWeightRepository

    init{
        val childWeightDao = ChildWeightDatabase.getInstance(application).childDataBaseDao()
        repository = ChildWeightRepository(childWeightDao)
        readAllData = repository.readAllData
        getWeightById = repository.getWeightById
    }

    fun addChildWeight(child: ChildWeight){
        viewModelScope.launch(Dispatchers.IO){
            repository.addChild(child)

        }
    }
    fun addGameWeight( gameWeight: GameWeight){
        viewModelScope.launch(Dispatchers.IO){
            repository.addGameWeight(gameWeight)
        }
    }

/*    fun getWeightById(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getWeightById(id)
        }
    }

 */
   // val getWeightById : LiveData<List<Float>> = repository.getWeightById

}
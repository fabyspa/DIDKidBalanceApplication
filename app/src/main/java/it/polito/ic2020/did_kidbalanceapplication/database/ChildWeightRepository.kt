package it.polito.ic2020.did_kidbalanceapplication.database

import android.util.Log
import androidx.lifecycle.LiveData

class ChildWeightRepository (private val childDatabaseDao: ChildDatabaseDao) {

    val readAllData: LiveData<List<ChildWeight>> = childDatabaseDao.readAllData()

    suspend fun addChild(user: ChildWeight){
        childDatabaseDao.insert(user)
    }

    suspend fun addGameWeight( gameWeight: GameWeight){
        childDatabaseDao.insertGameWeight(gameWeight)
    }

    suspend fun getWeight(int: Int){
        childDatabaseDao.getWeightById(int)
    }
}
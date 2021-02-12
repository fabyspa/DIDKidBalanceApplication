package it.polito.ic2020.did_kidbalanceapplication.database

import androidx.lifecycle.LiveData

class ChildWeightRepository(private val childDatabaseDao: ChildDatabaseDao) {

    val readAllData: LiveData<List<ChildWeight>> = childDatabaseDao.readAllData()

    suspend fun addChild(user: ChildWeight){
        childDatabaseDao.insert(user)
    }

    suspend fun addGameWeight( gameWeight: GameWeight){
        childDatabaseDao.insertGameWeight(gameWeight)
    }

/*    fun getWeightById(id: Int){
        childDatabaseDao.getWeightById(id)
    }

 */
    val getWeightById : LiveData<List<Float>> = childDatabaseDao.getWeightById(1)
}
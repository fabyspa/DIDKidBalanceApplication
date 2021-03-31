package it.polito.ic2020.did_kidbalanceapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChildDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weight: ChildWeight)

    @Update
    fun update(weight: ChildWeight)

    @Query("SELECT * FROM daily_weight_child_table ORDER BY id ASC")
    fun readAllData():LiveData<List<ChildWeight>>

    @Query("SELECT weight FROM game_weight_table WHERE id=:id")
    fun getWeightById(id: Int):MutableList<Float>

    @Query("SELECT picture FROM daily_weight_child_table WHERE nome=:nome")
    fun getPicture(nome: String):Int


    @Query("SELECT date FROM game_weight_table WHERE id=:id")
    fun getDateById(id: Int):MutableList<Long>

    @Query("SELECT altezza FROM daily_weight_child_table WHERE id=:id")
    fun getHeightById(id: Int):MutableList<Double>

    @Query("SELECT * FROM daily_weight_child_table WHERE id=:id")
    fun getAllChildData(id: Int):ChildWeight

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameWeight(gameWeight: GameWeight)

}
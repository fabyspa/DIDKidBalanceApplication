package it.polito.ic2020.did_kidbalanceapplication.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChildDatabaseDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weight: ChildWeight)

    @Update
    fun update(weight: ChildWeight)

    @Query("SELECT * FROM daily_weight_child_table ORDER BY id ASC")
    fun readAllData():LiveData<List<ChildWeight>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameWeight(gameWeight: GameWeight)

    @Query("SELECT weight FROM game_weight_table WHERE id LIKE :id")
    fun getWeightById(id: Int): Float

}
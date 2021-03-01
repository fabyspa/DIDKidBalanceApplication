package it.polito.ic2020.did_kidbalanceapplication.database

import android.os.Message
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.jjoe64.graphview.series.DataPoint

@Dao
interface ChildDatabaseDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weight: ChildWeight)

    @Update
    fun update(weight: ChildWeight)

    @Query("SELECT * FROM daily_weight_child_table ORDER BY id ASC")
    fun readAllData():LiveData<List<ChildWeight>>

    @Query("SELECT weight FROM game_weight_table WHERE id=:id")
    fun getWeightById(id :Int ):MutableList<Float>

    @Query("SELECT date FROM game_weight_table WHERE id=:id")
    fun getDateById(id :Int ):MutableList<Long>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameWeight(gameWeight: GameWeight)

}
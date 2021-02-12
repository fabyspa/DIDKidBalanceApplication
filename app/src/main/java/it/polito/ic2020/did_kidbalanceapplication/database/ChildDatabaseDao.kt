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

    @Query("SELECT * FROM game_weight_table WHERE date=1612458457855 AND id=1")
    fun getWeightById():List<GameWeight>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameWeight(gameWeight: GameWeight)

}
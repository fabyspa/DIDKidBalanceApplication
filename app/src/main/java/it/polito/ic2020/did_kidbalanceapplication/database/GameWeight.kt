package it.polito.ic2020.did_kidbalanceapplication.database

import androidx.room.Entity

@Entity(tableName= "game_weight_table", primaryKeys = ["id", "date"])
data class GameWeight  (
        var id: Int,
        var date: Long,
        var weight: Float
)

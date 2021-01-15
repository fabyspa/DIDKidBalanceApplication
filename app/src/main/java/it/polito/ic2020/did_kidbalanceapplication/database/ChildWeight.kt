package it.polito.ic2020.did_kidbalanceapplication.database

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "daily_weight_child_table")
data class ChildWeight (
    @PrimaryKey(autoGenerate = true )
    var id:Int,
    var nome: String,
    val surname: String,
    val altezza: Double,
    val gender: String,
    val picture: Int
)
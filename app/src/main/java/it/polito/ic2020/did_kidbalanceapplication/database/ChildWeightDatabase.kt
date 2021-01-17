package it.polito.ic2020.did_kidbalanceapplication.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import it.polito.ic2020.did_kidbalanceapplication.AddChild.AddChild

@Database(entities = [ChildWeight::class, GameWeight::class], version = 1, exportSchema = false)
abstract class ChildWeightDatabase :RoomDatabase(){
    abstract fun childDataBaseDao():ChildDatabaseDao

    //companion objects allows clients to access the methods without instantiating the class
    companion object{
        @Volatile
        private var INSTANCE : ChildWeightDatabase?=null
        fun getInstance(context: Context): ChildWeightDatabase{
            //only one thread of execution at a time
                val tempInstance = INSTANCE
                if (tempInstance!= null) {
                    return tempInstance
                }
            synchronized(this) {

            val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChildWeightDatabase::class.java,
                        "childWeight_history_database")
                        .build()
                    INSTANCE=instance
                    Log.i("AddChild","instanza creata")
                    return instance
                }

            }
        }



    }

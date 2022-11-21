package com.tmhwrd.weather.db

import  android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ForecastDao {
    @Query("select * from databaseforecast")
    fun getForecast(): LiveData<List<DatabaseForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( Forecast: List<DatabaseForecast>)
}



@Database(entities = [DatabaseForecast::class], version = 1)
abstract class ForecastDatabase: RoomDatabase() {
    abstract val ForecastDao: ForecastDao
}

private lateinit var INSTANCE: ForecastDatabase

fun getDatabase(context: Context): ForecastDatabase {
    synchronized(ForecastDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java,
                "Forecast").build()
        }
    }
    return INSTANCE
}

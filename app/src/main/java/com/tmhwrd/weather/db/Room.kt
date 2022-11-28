package com.tmhwrd.weather.db

import  android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ForecastDao {
    @Query("select * from forecast")
    fun getAll(): LiveData<List<Forecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( Forecast: List<Forecast>)
}

@Database(entities = [Forecast::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class ForecastDatabase: RoomDatabase() {
    abstract val forecastDao: ForecastDao
}

private lateinit var INSTANCE: ForecastDatabase

fun getDatabase(context: Context): ForecastDatabase {
    synchronized(ForecastDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java,
                "forecast").build()
        }
    }
    return INSTANCE
}

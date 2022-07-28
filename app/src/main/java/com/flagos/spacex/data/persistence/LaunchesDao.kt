package com.flagos.spacex.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flagos.spacex.domain.LaunchItem

@Dao
interface LaunchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchItem>)

    @Query("SELECT * FROM LaunchItem")
    suspend fun getLaunches(): List<LaunchItem>
}

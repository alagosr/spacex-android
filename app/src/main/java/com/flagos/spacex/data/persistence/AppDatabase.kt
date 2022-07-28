package com.flagos.spacex.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flagos.spacex.domain.LaunchItem

@Database(entities = [LaunchItem::class], version = 1, exportSchema = true)
@TypeConverters(value = [RoomConverters::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun launchesDao(): LaunchesDao
}

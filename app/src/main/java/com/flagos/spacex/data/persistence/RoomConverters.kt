package com.flagos.spacex.data.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.flagos.spacex.domain.LaunchSiteItem
import com.flagos.spacex.domain.LinksItem
import com.flagos.spacex.domain.RocketItem
import com.squareup.moshi.Moshi
import javax.inject.Inject

@ProvidedTypeConverter
class RoomConverters @Inject constructor(private val moshi: Moshi) {

    @TypeConverter
    fun fromRocketItem(data: RocketItem): String = moshi.adapter(RocketItem::class.java).toJson(data)

    @TypeConverter
    fun toRocketItem(data: String): RocketItem? = moshi.adapter(RocketItem::class.java).fromJson(data)

    @TypeConverter
    fun fromLinksItem(data: LinksItem): String = moshi.adapter(LinksItem::class.java).toJson(data)

    @TypeConverter
    fun toLinksItem(data: String): LinksItem? = moshi.adapter(LinksItem::class.java).fromJson(data)

    @TypeConverter
    fun fromLaunchSiteItem(data: LaunchSiteItem): String = moshi.adapter(LaunchSiteItem::class.java).toJson(data)

    @TypeConverter
    fun toLaunchSiteItem(data: String): LaunchSiteItem? = moshi.adapter(LaunchSiteItem::class.java).fromJson(data)
}

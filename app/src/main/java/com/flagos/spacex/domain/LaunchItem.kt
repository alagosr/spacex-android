package com.flagos.spacex.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LaunchItem(
    @field:Json(name = "flight_number") val flightNumber: Int,
    @field:Json(name = "mission_name") val missionName: String,
    @field:Json(name = "launch_year") val launchYear: String,
    @field:Json(name = "launch_date_utc") val launchDateUtc: String,
    @field:Json(name = "rocket") val rocket: RocketItem,
    @field:Json(name = "launch_site") val siteId: LaunchSiteItem,
    @field:Json(name = "details") val details: String?,
    @field:Json(name = "links") val links: LinksItem,
)

@JsonClass(generateAdapter = true)
data class RocketItem(
    @field:Json(name = "rocket_id") val rocketId: String,
    @field:Json(name = "rocket_name") val rocketName: String,
    @field:Json(name = "rocket_type") val rocketType: String?,
)

@JsonClass(generateAdapter = true)
data class LaunchSiteItem(
    @field:Json(name = "site_id") val siteId: String,
    @field:Json(name = "site_name") val siteName: String,
)

@JsonClass(generateAdapter = true)
data class LinksItem(
    @field:Json(name = "mission_patch") val missionPatch: String?,
    @field:Json(name = "mission_patch_small") val missionPatchSmall: String?,
)

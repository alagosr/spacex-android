package com.flagos.spacex

import com.flagos.spacex.domain.LaunchItem
import com.flagos.spacex.domain.LaunchSiteItem
import com.flagos.spacex.domain.LinksItem
import com.flagos.spacex.domain.RocketItem

object MockUtil {

    private fun mockLaunchItem(): LaunchItem {
        return LaunchItem(
            uid = 0,
            flightNumber = 1,
            missionName = "FalconSat",
            dateInUnix = 1143239400,
            rocket = RocketItem(
                id = "falcon1",
                name = "Falcon 1",
                type = "Merlin A"
            ),
            site = LaunchSiteItem(
                id = "kwajalein_atoll",
                name = "Kwajalein Atoll Omelek Island"
            ),
            details = "Engine failure at 33 seconds and loss of vehicle",
            links = LinksItem(
                missionPatch = "https://images2.imgbox.com/40/e3/GypSkayF_o.png",
                missionPatchSmall = "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"
            )
        )
    }

    fun mockLaunchItemList() = listOf(mockLaunchItem())
}

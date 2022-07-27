package com.flagos.spacex.utils

import java.text.SimpleDateFormat
import java.util.*

fun getLocalTimeFromUnix(unixTime: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    simpleDateFormat.timeZone = Calendar.getInstance().timeZone
    return simpleDateFormat.format(Date(unixTime * 1000))
}

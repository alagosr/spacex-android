package com.flagos.spacex.utils

import android.widget.ImageView
import com.flagos.spacex.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?) {
    Picasso.get().load(url)
        .fit()
        .centerInside()
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_image_24)
        .into(this)
}

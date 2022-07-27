package com.flagos.spacex.presentation.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.flagos.spacex.R
import com.flagos.spacex.databinding.LaunchItemBinding
import com.flagos.spacex.domain.LaunchItem
import com.flagos.spacex.utils.getLocalTimeFromUnix
import com.squareup.picasso.Picasso

class LaunchViewHolder(
    private val binding: LaunchItemBinding,
    private val onClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var launchItem: LaunchItem
    private var context: Context = binding.root.context

    init {
        binding.root.setOnClickListener { onClick.invoke(launchItem.details.orEmpty()) }
    }

    fun bind(launch: LaunchItem) {
        this.launchItem = launch
        with(binding) {
            textFlightNumber.text = String.format(context.getString(R.string.flight_number), launch.flightNumber)
            textLaunchSite.text = launch.site.name
            textLaunchDate.text = getLocalTimeFromUnix(launch.dateInUnix)
            textRocketName.text = String.format(context.getString(R.string.rocket_name), launch.rocket.name)
            textRocketType.text = String.format(context.getString(R.string.rocket_type), launch.rocket.type)
            textMissionName.text = launch.missionName

            Picasso.get().load(launch.links.missionPatchSmall)
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(imagePatch)
        }
    }
}

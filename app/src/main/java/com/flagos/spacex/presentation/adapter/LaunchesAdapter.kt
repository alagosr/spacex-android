package com.flagos.spacex.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.flagos.spacex.databinding.LaunchItemBinding
import com.flagos.spacex.domain.LaunchItem
import com.flagos.spacex.presentation.adapter.viewholder.LaunchViewHolder

class LaunchesAdapter(private val onClick: (String) -> Unit) : ListAdapter<LaunchItem, LaunchViewHolder>(LaunchesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LaunchViewHolder(LaunchItemBinding.inflate(inflater, parent, false), onClick)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class LaunchesDiff : DiffUtil.ItemCallback<LaunchItem>() {
        override fun areItemsTheSame(oldItem: LaunchItem, newItem: LaunchItem): Boolean = oldItem.flightNumber == newItem.flightNumber
        override fun areContentsTheSame(oldItem: LaunchItem, newItem: LaunchItem): Boolean = oldItem == newItem
    }
}

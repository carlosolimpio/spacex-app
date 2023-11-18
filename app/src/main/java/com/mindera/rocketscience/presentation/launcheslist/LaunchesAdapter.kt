package com.mindera.rocketscience.presentation.launcheslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.LayoutLaunchItemBinding
import com.mindera.rocketscience.domain.common.calculateDaysFromToday
import com.mindera.rocketscience.domain.common.convertToDate
import com.mindera.rocketscience.domain.common.convertToTime
import com.mindera.rocketscience.domain.launcheslist.Launch
import kotlin.math.absoluteValue

class LaunchesAdapter : RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Launch>() {
        override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem.missionName == newItem.missionName
        }
        override fun areContentsTheSame(oldItem: Launch, newItem: Launch) = oldItem == newItem
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(list: List<Launch>) {
        asyncListDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        val binding = LayoutLaunchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return LaunchesViewHolder(binding)
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.bind(data)
    }

    inner class LaunchesViewHolder(
        private val binding: LayoutLaunchItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(launch: Launch) {
            binding.apply {
                missionName.text = launch.missionName
                dateTimeValue.text = context.getString(
                    R.string.date_at_time,
                    launch.launchDate.convertToDate(),
                    launch.launchDate.convertToTime(),
                )
                rocketAlias.text = context.getString(
                    R.string.rocket_info,
                    launch.rocketName,
                    launch.rocketType,
                )

                val days = launch.launchDate.calculateDaysFromToday()
                daysLaunch.text = if (days > 0) {
                    context.getString(R.string.days_now, context.getString(R.string.from))
                } else {
                    context.getString(R.string.days_now, context.getString(R.string.since))
                }

                daysLaunchValue.text = days.absoluteValue.toString()

                launchSuccessIcon.setImageResource(
                    if (launch.wasLaunchSuccessful) {
                        R.drawable.icon_done
                    } else {
                        R.drawable.icon_failed
                    },
                )

                Glide.with(context)
                    .load(launch.missionPatchSmallUrl)
                    .fitCenter()
                    .placeholder(R.drawable.icon_image_loading)
                    .error(R.drawable.icon_image_failed)
                    .into(patchImage)

                launchCard.setOnClickListener {

                }
            }
        }
    }
}

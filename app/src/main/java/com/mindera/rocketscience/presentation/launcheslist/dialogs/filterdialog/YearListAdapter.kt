package com.mindera.rocketscience.presentation.launcheslist.dialogs.filterdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindera.rocketscience.databinding.LayoutYearItemBinding

class YearListAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<YearListAdapter.YearListViewHolder>() {

    private val yearsChecked = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearListViewHolder {
        val binding = LayoutYearItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return YearListViewHolder(binding)
    }

    override fun getItemCount() = data.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: YearListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun getYearsChecked() = yearsChecked

    fun clearYearsChecked() {
        yearsChecked.clear()
    }

    inner class YearListViewHolder(
        private val binding: LayoutYearItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(year: String) {
            binding.checkBox.apply {
                text = year
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        yearsChecked.add(year)
                    } else {
                        yearsChecked.remove(year)
                    }
                }
            }
        }
    }
}

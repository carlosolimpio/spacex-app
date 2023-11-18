package com.mindera.rocketscience.presentation.launcheslist.filterdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.databinding.LayoutCustomFilterDialogBinding
import com.mindera.rocketscience.presentation.launcheslist.SortOrder

class FilterDialog(
    private val years: List<String>,
    private val onFilter: (yearsChecked: List<String>, wasSuccessOnly: Boolean, sortBy: SortOrder) -> Unit,
    private val onClearFilter: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = LayoutCustomFilterDialogBinding.inflate(layoutInflater)
        val yearsAdapter = YearListAdapter(years)
        with(dialogBinding.rvYears) {
            adapter = yearsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        val yearsChecked = yearsAdapter.getYearsChecked()

        return AlertDialog.Builder(activity)
            .setView(dialogBinding.root)
            .setTitle("Filter the launches list")
            .setPositiveButton("Filter") { dialog, _ ->
                val checkedId = dialogBinding.rgSortedBy.checkedRadioButtonId
                if (checkedId == -1) {
                    onFilter(
                        yearsChecked,
                        dialogBinding.switchWasSuccessOnly.isChecked,
                        SortOrder.NOT_CHECKED
                    )
                } else {
                    val sortBy = dialogBinding.rgSortedBy.findViewById<RadioButton>(checkedId).text.toString()
                    onFilter(
                        yearsChecked,
                        dialogBinding.switchWasSuccessOnly.isChecked,
                        enumValueOf(sortBy)
                    )
                }

                dialog.dismiss()
            }
            .setNegativeButton("Clear Filters") { _, _ ->
                dialogBinding.rgSortedBy.clearCheck()
                dialogBinding.switchWasSuccessOnly.isChecked = false
                yearsAdapter.clearYearsChecked()
                onClearFilter()
            }
            .setCancelable(true)
            .create()
    }

    companion object {
        const val TAG = "FilterDialog"
    }
}

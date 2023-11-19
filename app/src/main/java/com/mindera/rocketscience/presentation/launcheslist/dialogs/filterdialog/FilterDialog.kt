package com.mindera.rocketscience.presentation.launcheslist.dialogs.filterdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.LayoutCustomFilterDialogBinding
import com.mindera.rocketscience.presentation.launcheslist.SortOrder

class FilterDialog(
    private val years: List<String>,
    private val onFilter: (
        yearsChecked: List<String>,
        wasSuccessOnly: Boolean,
        sortBy: SortOrder
    ) -> Unit,
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
            .setTitle(getString(R.string.filter_the_launches_list))
            .setPositiveButton(getString(R.string.filter)) { dialog, _ ->
                val checkedId = dialogBinding.rgSortedBy.checkedRadioButtonId
                if (checkedId == NOT_CHECKED) {
                    onFilter(
                        yearsChecked,
                        dialogBinding.switchWasSuccessOnly.isChecked,
                        SortOrder.NOT_CHECKED
                    )
                } else {
                    onFilter(
                        yearsChecked,
                        dialogBinding.switchWasSuccessOnly.isChecked,
                        enumValueOf(
                            dialogBinding.rgSortedBy
                                .findViewById<RadioButton>(checkedId)
                                .text
                                .toString()
                        )
                    )
                }

                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.clear_filters)) { _, _ ->
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
        private const val NOT_CHECKED = -1
    }
}

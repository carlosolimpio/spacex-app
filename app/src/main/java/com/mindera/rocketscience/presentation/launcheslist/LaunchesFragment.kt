package com.mindera.rocketscience.presentation.launcheslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.FragmentLaunchesBinding
import com.mindera.rocketscience.databinding.LayoutCustomFilterDialogBinding
import com.mindera.rocketscience.domain.launcheslist.Launch
import com.mindera.rocketscience.presentation.launcheslist.filterdialog.FilterDialog
import com.mindera.rocketscience.presentation.launcheslist.filterdialog.YearListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchesFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentLaunchesBinding
    private val viewModel: LaunchesViewModel by viewModels()

    private lateinit var launchesAdapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().addMenuProvider(this)

        binding = FragmentLaunchesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.options_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_filter -> {
                viewModel.fetchLaunchesYears()
                lifecycleScope.launch {
                    viewModel.yearsState.collect { years ->
                        val dialog = FilterDialog(
                            years.toList(),
                            onFilter = { yearsChecked, wasSuccessOnly, sortBy ->
                                viewModel.applyFilters(
                                    yearsChecked,
                                    wasSuccessOnly,
                                    sortBy
                                )
                            },
                            onClearFilter = { viewModel.fetchLaunches() }
                        )
                        dialog.show(childFragmentManager, FilterDialog.TAG)
                    }
                }
                true
            }
            else -> false
        }
    }

    private fun initLaunchesList(launches: List<Launch>) {
        launchesAdapter = LaunchesAdapter()
        launchesAdapter.saveData(launches)

        with(binding.rvLaunchesList) {
            adapter = launchesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { response ->
                when (response) {
                    is LaunchesState.Success -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            rvLaunchesList.visibility = View.VISIBLE
                        }
                        initLaunchesList(response.data)
                    }
                    is LaunchesState.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    LaunchesState.Loading -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            rvLaunchesList.visibility = View.GONE
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.notFoundState.collect {
                if (it.isNotBlank()) Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}

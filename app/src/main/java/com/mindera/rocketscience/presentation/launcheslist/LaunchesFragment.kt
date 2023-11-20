package com.mindera.rocketscience.presentation.launcheslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.FragmentLaunchesBinding
import com.mindera.rocketscience.domain.common.UiState
import com.mindera.rocketscience.domain.launcheslist.Launch
import com.mindera.rocketscience.presentation.launcheslist.dialogs.filterdialog.FilterDialog
import com.mindera.rocketscience.presentation.launcheslist.dialogs.linksdialog.LinkChooserDialog
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
        savedInstanceState: Bundle?
    ): View {
        requireActivity().addMenuProvider(this)

        binding = FragmentLaunchesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchLaunches()
        initObservers()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.options_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_filter -> {
                viewModel.fetchYears()
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
        launchesAdapter = LaunchesAdapter(
            onMissionClick = {
                LinkChooserDialog(it).show(childFragmentManager, LinkChooserDialog.TAG)
            }
        )
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
                    is UiState.Success -> {
                        showProgress(false)
                        initLaunchesList(response.data)
                    }
                    is UiState.Error -> {
                        showProgress(false)
                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    }
                    UiState.Loading -> { showProgress(true) }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.notFoundState.collect {
                if (it.isNotBlank()) Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showProgress(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                rvLaunchesList.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvLaunchesList.visibility = View.VISIBLE
            }
        }
    }
}

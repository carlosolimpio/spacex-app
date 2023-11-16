package com.mindera.rocketscience.presentation.launcheslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.databinding.FragmentLaunchesBinding
import com.mindera.rocketscience.domain.launcheslist.Launch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private lateinit var binding: FragmentLaunchesBinding
    private val viewModel: LaunchesViewModel by viewModels()

    private lateinit var launchesAdapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
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
                        initLaunchesList(response.data)
                    }
                    is LaunchesState.Error -> {
                        Log.d("OLIMPIO", "initObservers: LaunchesFragment: $response")
                    }
                    LaunchesState.Loading -> {
                        Log.d("OLIMPIO", "initObservers: LaunchesFragment: $response")
                    }
                }
            }
        }
    }
}

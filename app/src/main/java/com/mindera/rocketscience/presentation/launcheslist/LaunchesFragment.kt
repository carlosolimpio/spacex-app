package com.mindera.rocketscience.presentation.launcheslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindera.rocketscience.databinding.FragmentLaunchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private lateinit var binding: FragmentLaunchesBinding
    private val viewModel: LaunchesViewModel by viewModels()

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

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { response ->
                when (response) {
                    is LaunchesState.Success -> {
                        Log.d("OLIMPIO", "initObservers: LaunchesFragment: ${response.data.first()}")
                        Log.d("OLIMPIO", "initObservers: LaunchesFragment: ${response.data.count()}")
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

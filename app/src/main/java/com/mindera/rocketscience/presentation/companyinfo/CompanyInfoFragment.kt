package com.mindera.rocketscience.presentation.companyinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindera.rocketscience.databinding.FragmentCompanyInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyInfoFragment : Fragment() {

    private lateinit var binding: FragmentCompanyInfoBinding

    private val companyViewModel: CompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCompanyInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            companyViewModel.state.collect { response ->
                when (response) {
                    is CompanyState.Success -> {
                        Log.d("OLIMPIO", "initObservers: $response")
                    }
                    is CompanyState.Error -> {
                        Log.d("OLIMPIO", "initObservers: $response")
                    }
                    CompanyState.Loading -> {
                        Log.d("OLIMPIO", "initObservers: $response")
                    }
                }
            }
        }
    }
}

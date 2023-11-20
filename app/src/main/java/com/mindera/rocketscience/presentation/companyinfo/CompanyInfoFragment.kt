package com.mindera.rocketscience.presentation.companyinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.FragmentCompanyInfoBinding
import com.mindera.rocketscience.domain.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyInfoFragment : Fragment() {

    private lateinit var binding: FragmentCompanyInfoBinding

    private val companyViewModel: CompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyViewModel.fetchCompany()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            companyViewModel.state.collect { response ->
                when (response) {
                    is UiState.Success -> {
                        binding.apply {
                            showProgress(false)
                            companySumaryText.text = getString(
                                R.string.company_summary,
                                response.data.name,
                                response.data.founder,
                                response.data.yearFounded,
                                response.data.employees,
                                response.data.launchSites,
                                response.data.valuation
                            )
                        }
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                    }
                    UiState.Loading -> { showProgress(true) }
                }
            }
        }
    }

    private fun showProgress(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                companySumaryText.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                companySumaryText.visibility = View.VISIBLE
            }
        }
    }
}

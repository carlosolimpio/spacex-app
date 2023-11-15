package com.mindera.rocketscience.domain.companyinfo

import javax.inject.Inject

class CompanyUseCase @Inject constructor(
    private val repository: CompanyRepository
) {
    suspend operator fun invoke() = repository.getCompany()
}

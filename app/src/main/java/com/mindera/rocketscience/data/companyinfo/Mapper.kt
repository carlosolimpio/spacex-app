package com.mindera.rocketscience.data.companyinfo

import com.mindera.rocketscience.data.companyinfo.local.entities.CompanyEntity
import com.mindera.rocketscience.data.companyinfo.remote.dto.CompanyDto
import com.mindera.rocketscience.domain.companyinfo.Company

fun CompanyDto.toCompanyEntity() = CompanyEntity(
    name = name,
    founder = founder,
    yearFounded = yearFounded,
    employees = employees,
    launchSites = launchSites,
    valuation = valuation
)

fun CompanyDto.toCompany() = Company(
    name = name,
    founder = founder,
    yearFounded = yearFounded,
    employees = employees,
    launchSites = launchSites,
    valuation = valuation
)

fun CompanyEntity.toCompany() = Company(
    name = name,
    founder = founder,
    yearFounded = yearFounded,
    employees = employees,
    launchSites = launchSites,
    valuation = valuation
)

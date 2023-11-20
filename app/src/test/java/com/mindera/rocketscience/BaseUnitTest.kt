package com.mindera.rocketscience

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.companyinfo.Company
import com.mindera.rocketscience.domain.launcheslist.Launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
open class BaseUnitTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }

    protected val errorResponse = DataResponse.Error("Custom test error message")

    protected val companyResponse = DataResponse.Success(
        Company(
            "SpaceX",
            "Elon Musk",
            2002,
            7000,
            3,
            27500000000
        )
    )

    protected val yearsSet = setOf("2022", "2023")
    protected val launchesResponse = DataResponse.Success(
        listOf(
            Launch(
                "mission1",
                "25/12/2023",
                "2023",
                true,
                "santa1",
                "sled1",
                "missionpatch1",
                "missionpatchsmall1",
                "articlelink1",
                "wikilink1",
                "videolink1"
            ),
            Launch(
                "mission2",
                "25/12/2022",
                "2022",
                false,
                "santa2",
                "sled2",
                "missionpatch2",
                "missionpatchsmall2",
                "articlelink2",
                "wikilink2",
                "videolink2"
            ),
            Launch(
                "mission3",
                "31/12/2023",
                "2023",
                false,
                "santa3",
                "sled3",
                "missionpatch3",
                "missionpatchsmall3",
                "articlelink3",
                "wikilink3",
                "videolink3"
            )
        )
    )
}

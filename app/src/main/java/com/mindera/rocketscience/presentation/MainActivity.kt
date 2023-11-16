package com.mindera.rocketscience.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mindera.rocketscience.R
import com.mindera.rocketscience.presentation.companyinfo.CompanyInfoFragment
import com.mindera.rocketscience.presentation.launcheslist.LaunchesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        with(supportFragmentManager) {
            beginTransaction()
                .replace(R.id.container1, CompanyInfoFragment(), null)
                .commitNow()

            beginTransaction()
                .replace(R.id.container2, LaunchesFragment(), null)
                .commitNow()
        }
    }
}

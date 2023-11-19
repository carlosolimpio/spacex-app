package com.mindera.rocketscience.presentation

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.mindera.rocketscience.R
import com.mindera.rocketscience.presentation.companyinfo.CompanyInfoFragment
import com.mindera.rocketscience.presentation.launcheslist.LaunchesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.layout_app_bar)

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

package com.dnguy38.lastplayed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dnguy38.lastplayed.ui.info.InfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // if (savedInstanceState == null) {
        //     supportFragmentManager.beginTransaction()
        //         .replace(R.id.container, LoginFragment.newInstance())
        //         .commitNow()
        // }

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.userFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingsFragment -> true
            R.id.infoFragment -> {
                val infoFragment = InfoFragment.newInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment, infoFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
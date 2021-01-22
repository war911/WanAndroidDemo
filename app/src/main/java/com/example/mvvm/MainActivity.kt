package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_drawer_layout.*
import kotlinx.android.synthetic.main.navigation_view_head_layout.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(Toolbar(this))
        hideSystemUi()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()


    }

    private fun hideSystemUi() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val window = window
        val params = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }

    private fun initNavigation() {
        navController = findNavController(R.id.nav_host_fragment_container)
        val set = setOf(R.id.favoritesFragment, R.id.leagueTableFragment, R.id.kindFragment)
        appBarConfiguration = AppBarConfiguration(set, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationDrawer.setupWithNavController(navController)
        navigationDrawer.getHeaderView(0).tvHead?.text = getString(R.string.login)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
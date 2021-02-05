package com.example.mvvm

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.KEY_NAME
import com.example.mvvm.model.KEY_PASS
import com.example.mvvm.model.MainViewModel
import com.example.mvvm.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_view_head_layout.*
import kotlinx.android.synthetic.main.navigation_view_head_layout.view.*
import kotlinx.android.synthetic.main.two_layout.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var model: MainViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(Toolbar(this))
        hideSystemUi()

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        model = ViewModelProvider(this).get(MainViewModel::class.java)
        Log.d(TAG, "onCreate: model 是 ${model.toString()}")
        setContentView(binding.root)
        initNavigation()
        model.name.observe(this, androidx.lifecycle.Observer {
            binding.navigationDrawer.getHeaderView(0).tvHead.text = it
        })

        model.userRank.observe(this, androidx.lifecycle.Observer {rank->
            Log.d(TAG, "onCreate: ?? rank  ${rank}")
            binding.navigationDrawer.getHeaderView(0).let {
                rank?.let {
                    tvLevel.text = String.format("lv: %d", rank.data.level)
                    tvCount.text = String.format("当前积分: %s", rank.data.coinCount)
                    tvRank.text = String.format("排行: %s", rank.data.rank)

                    tvLevel.visibility = View.VISIBLE
                    tvCount.visibility = View.VISIBLE
                    tvRank.visibility = View.VISIBLE
                }
            }
        })

        login()
    }

    private fun login() {
        val name = PrefUtils.getValue(applicationContext, KEY_NAME, null)
        val pass = PrefUtils.getValue(applicationContext, KEY_PASS, null)

        Log.d(TAG, "login: Name $name")
        Log.d(TAG, "login: Pass $pass")
        model.mainLogin(name, pass)
    }

    private fun hideSystemUi() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val window = window
        val params = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }

    public fun setUser(name: String){
        Log.d(TAG, "setUser: 数据$name")

        binding.navigationDrawer.getHeaderView(0)?.tvHead?.text = name
        Log.d(TAG, "setUser: ok ${binding.navigationDrawer.getHeaderView(0) == null}")
        Log.d(
            TAG, "setUser: {binding.navigationDrawer.getHeaderView(0) ${
                binding.navigationDrawer.getHeaderView(
                    0
                ).tv == null
            }"
        )
    }

    private fun initNavigation() {
        navController = findNavController(R.id.nav_host_fragment_container)
        val set = setOf(
            R.id.homeFragment,
            R.id.favoritesFragment,
            R.id.kindFragment
        )

        appBarConfiguration = AppBarConfiguration(set, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationDrawer.setupWithNavController(navController)
        binding.navigationDrawer.getHeaderView(0).tvHead?.let {
            it.text = getString(R.string.login)
            it.setOnClickListener {
                navController.navigate(R.id.action_global_loginFragment)
                drawerLayout.close()
            }
        }

        binding.navigationDrawer.menu[3].setOnMenuItemClickListener {
            Toast.makeText(this@MainActivity, "退出", Toast.LENGTH_SHORT).show()
            model.logOut()
            binding.navigationDrawer.menu[0].isChecked = false
            binding.navigationDrawer.menu[1].isChecked = false
            binding.navigationDrawer.menu[2].isChecked = false
            binding.navigationDrawer.menu[3].isChecked = false
            tvCount.visibility = View.INVISIBLE
            tvLevel.visibility = View.INVISIBLE
            tvRank.visibility = View.INVISIBLE
            navController.navigate(R.id.action_global_homeFragment)
            true
        }

        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.drawerLayout.let {
                if (it.isOpen) {
                    it.close()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}

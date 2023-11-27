package com.simonllano.safecash.ui.bottom

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.simonllano.safecash.R
import com.simonllano.safecash.databinding.ActivityBottomNavigationMainBinding
import com.simonllano.safecash.ui.login.LoginActivity
import com.simonllano.safecash.ui.perfil.PerfilViewModel
import com.simonllano.safecash.ui.splash.SplashActivity

class BottomNavigationMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationMainBinding
    private lateinit var bottomViewModel : BottomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityBottomNavigationMainBinding.inflate(layoutInflater)
        bottomViewModel = ViewModelProvider(this)[BottomViewModel::class.java]
        setContentView(binding.root)

        bottomViewModel.verifyUser()

        bottomViewModel.errorMSG.observe(this){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        bottomViewModel.userLoggedIn.observe(this){
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()

        }

        val navView: BottomNavigationView = binding.navView

        val navController =
            findNavController(R.id.nav_host_fragment_activity_bottom_navigation_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_main,
                R.id.navigation_income,
                R.id.navigation_costs,
                R.id.navigation_deductibles,
                R.id.navigation_perfil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
    private fun showErrorMSG(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    }
}
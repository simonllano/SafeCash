package com.simonllano.safecash.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simonllano.safecash.R
import android.content.Intent
import android.view.ViewTreeObserver
import com.simonllano.safecash.ui.bottom.BottomNavigationMainActivity
import com.simonllano.safecash.ui.login.LoginActivity


class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 1000 // 3 segundos (puedes ajustarlo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Usando View
        val rootView = window.decorView
        rootView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                rootView.viewTreeObserver.removeOnPreDrawListener(this)
                rootView.postDelayed({
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, splashTimeOut)
                return true
            }
        }
        )

    }
}




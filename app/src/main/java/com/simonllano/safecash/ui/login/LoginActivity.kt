package com.simonllano.safecash.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.data.UserRepository
import com.simonllano.safecash.ui.register.RegisterActivity
import com.simonllano.safecash.databinding.ActivityLoginBinding
import com.simonllano.safecash.ui.bottom.BottomNavigationMainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val view =loginBinding.root
        setContentView(view)

        loginViewModel.errorMSG.observe(this){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        loginViewModel.registerSuccess.observe(this){
            val intent = Intent(this, BottomNavigationMainActivity::class.java)
            startActivity(intent)
            finish()

        }

        loginBinding.createButton.setOnClickListener {//Aca se envia la orden para abrir la actividad
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }


        loginBinding.registerButton.setOnClickListener{
            val email = loginBinding.userEditText.text.toString()
            val password = loginBinding.passwordEditText.text.toString()
            loginViewModel.validateFields(email, password)

        }

    }
    private fun showErrorMSG(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    }
}
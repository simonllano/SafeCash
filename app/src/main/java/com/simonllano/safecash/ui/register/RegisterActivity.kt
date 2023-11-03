package com.simonllano.safecash.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) { //Si no es una actividad sino un fragmento
        super.onCreate(savedInstanceState)              //va con on create view
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        val view = registerBinding.root
        setContentView(view)

        registerViewModel.errorMSG.observe(this){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        registerViewModel.registerSuccess.observe(this){
            onBackPressedDispatcher.onBackPressed()
        }

        registerBinding.registerButton.setOnClickListener {
            val name = registerBinding.nameEditText.text.toString()
            val lastname = registerBinding.lastnameEditText.text.toString()
            val user = registerBinding.userregisterEditText.text.toString()
            val email = registerBinding.emailEditText.text.toString()
            val password = registerBinding.passwordEditText.text.toString()
            val reppassword = registerBinding.reppasswordEditText.text.toString()
            val age = registerBinding.ageEditText.text.toString()

            registerViewModel.validateFields(name, lastname, user, email, password, reppassword, age)

        }
        

    }

    private fun showErrorMSG(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    }
}

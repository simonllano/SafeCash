package com.simonllano.safecash.ui.perfil

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.simonllano.safecash.R
import com.simonllano.safecash.databinding.FragmentPerfilBinding
import com.simonllano.safecash.ui.login.LoginActivity


class PerfilFragment : Fragment() {


    private lateinit var perfilViewModel: PerfilViewModel
    private lateinit var perfilBinding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        perfilBinding = FragmentPerfilBinding.inflate(inflater, container, false)
        return perfilBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        perfilViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)


        perfilViewModel.userLoggedOut.observe(viewLifecycleOwner){
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }
        perfilViewModel.errorMsg.observe(viewLifecycleOwner) { msg ->
            showErrorMsg(msg)
        }

        perfilBinding.signOutButton.setOnClickListener{
            perfilViewModel.signOut()
        }


    }

    private fun observeViewModel() {
        perfilViewModel.errorMsg.observe(viewLifecycleOwner) { msg ->
            showErrorMsg(msg)
        }

        perfilViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                perfilBinding.name3TextView.text = "Nombre: ${user.name}"
                perfilBinding.lastname1TextView.text = "Apellido: ${user.lastname}"
                perfilBinding.email1TextView.text = "Correo: ${user.email}"
                perfilBinding.user1TextView.text = "Usuario: ${user.usuario}"
            }
        }

        perfilViewModel.loadCurrentUser()
    }

    private fun showErrorMsg(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    }
}


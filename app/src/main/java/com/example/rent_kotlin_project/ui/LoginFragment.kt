package com.example.rent_kotlin_project.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rent_kotlin_project.R
import com.example.rent_kotlin_project.Viewmodel.AuthViewModel
import com.example.rent_kotlin_project.databinding.FragmentLoginBinding
import com.example.rent_kotlin_project.models.Login.LoginRequest
import com.example.rent_kotlin_project.utils.Constants.TAG
import com.example.rent_kotlin_project.utils.Helper
import com.example.rent_kotlin_project.utils.NetworkResult
import com.example.rent_kotlin_project.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    val authViewModel by activityViewModels<AuthViewModel>()
    //  lateinit var authViewModel: AuthViewModel


    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            Helper.hideKeyboard(it)
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequest = getUserRequest()
                authViewModel.loginuser(userRequest)
            } else {
                showValidationErrors(validationResult.second)
            }

        }
//        bindObservers()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val mobilenum = binding.txtMobile.text.toString()
        val password = binding.txtPassword.text.toString()
        return authViewModel.validedfield(mobilenum, password)
    }

    private fun getUserRequest(): LoginRequest {
        return binding.run {
            LoginRequest(
                txtMobile.text.toString(),
                txtPassword.text.toString()
            )
        }


    }

    private fun showValidationErrors(error: String) {
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun bindObservers() {
        authViewModel.userResponseLivedata.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.key)
                    Log.d(TAG, tokenManager.saveToken(it.data.key).toString())
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
                is NetworkResult.Error -> {
                    showValidationErrors(it.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
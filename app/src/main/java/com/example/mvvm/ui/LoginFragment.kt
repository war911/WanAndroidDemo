package com.example.mvvm.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mvvm.MainActivity
import com.example.mvvm.R
import com.example.mvvm.model.LoginViewModel
import com.example.mvvm.model.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.btnSignUp


class LoginFragment : Fragment() {
    private lateinit var model:MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = (activity as MainActivity).model

        Log.d(TAG, "onCreate: model 是 ${model.toString()}")

        btnSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_global_signUpFragment)
        }
        btnLogin.setOnClickListener {
            model.mainLogin(editLoginName.text.toString(),editLoginPass.text.toString())
        }

        model.name.observe(viewLifecycleOwner, Observer {
            if (it != getString(R.string.login)) {
                hideSystemKeyBoard()
                view.findNavController().navigate(R.id.action_global_homeFragment)
            }
        })
    }

    private fun hideSystemKeyBoard(){
        val imm: InputMethodManager = activity
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editLoginName.windowToken, 0)
        imm.hideSoftInputFromWindow(editLoginPass.windowToken, 0)

    }


    companion object {
        private const val TAG = "LoginFragment"
    }
}

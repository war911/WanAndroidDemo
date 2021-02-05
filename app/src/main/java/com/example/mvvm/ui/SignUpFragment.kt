package com.example.mvvm.ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mvvm.MainActivity
import com.example.mvvm.R
import com.example.mvvm.model.SignUpViewModel
import kotlinx.android.synthetic.main.signup_fragment.*

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
        private const val TAG = "SignUpFragment"
    }

    private lateinit var viewModel: SignUpViewModel

    //private lateinit var bing
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        btnSignUp.setOnClickListener {
            viewModel.signUp(editName.text.toString(), editPass.text.toString(), editRePass.text.toString())
        }
        viewModel.go.observe(viewLifecycleOwner, Observer {
            if (it) {
                hideSystemKeyBoard()
                Navigation.findNavController(editName).navigate(R.id.action_global_homeFragment)
            }
        })
        viewModel.name.observe(viewLifecycleOwner, Observer {
            Log.d(Companion.TAG, "onActivityCreated: 数据$it")
            (activity as MainActivity).setUser(it)
        })
    }

    private fun hideSystemKeyBoard(){
        val imm: InputMethodManager = activity
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editName.windowToken, 0)
        imm.hideSoftInputFromWindow(editPass.windowToken, 0)

    }

}
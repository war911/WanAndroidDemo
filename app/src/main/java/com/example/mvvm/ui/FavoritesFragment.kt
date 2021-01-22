package com.example.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.mvvm.model.OneViewModel
import com.example.mvvm.databinding.OneLayoutBinding

class FavoritesFragment : Fragment() {
    companion object {
        private const val TAG = "OneFragment"
    }

    lateinit var bing: OneLayoutBinding
    lateinit var model: OneViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bing = OneLayoutBinding.inflate(inflater)
        /**
         * *ViewModel viewModel = new ViewModelProvider(getActivity()/this).get(*ViewModel.class);
         */
        model = ViewModelProvider(this).get(OneViewModel::class.java)
        bing.oneModel = model
        bing.lifecycleOwner = this
        model.mutableLiveData.observe(viewLifecycleOwner) {item ->
            run {
                Log.d(TAG, "mutableLiveData change : $item")
            }
        }
        return bing.root
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        model.getMainBanner()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
    }
}
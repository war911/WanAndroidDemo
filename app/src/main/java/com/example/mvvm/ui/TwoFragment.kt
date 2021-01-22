package com.example.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvm.databinding.TwoLayoutBinding

class TwoFragment :Fragment() {
    companion object{
        private const val TAG = "TwoFragment"
    }

    lateinit var bing: TwoLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bing = TwoLayoutBinding.inflate(inflater,container,false)
        return bing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        bing.tv.text = "TWO"
    }
}
package com.example.mvvm.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.databinding.WebLayoutBinding
import com.example.mvvm.model.WebModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient


class WebContentFragment : Fragment() {
    companion object {
        private const val TAG = "WebContentFragment"
    }

    lateinit var bing: WebLayoutBinding
    lateinit var model: WebModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bing = WebLayoutBinding.inflate(inflater)

        val url = arguments?.getString("key")
        Log.d(TAG, "onCreateView: 数据$url")

        var mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(bing.webLl, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(Color.BLUE)
            .createAgentWeb()
            .ready()
            .go(url)

        mAgentWeb.webCreator.webView.webChromeClient = mWebChromeClient
        mAgentWeb.webCreator.webView.webViewClient = mWebViewClient


        /**
         * *ViewModel viewModel = new ViewModelProvider(getActivity()/this).get(*ViewModel.class);
         */
        model = ViewModelProvider(this).get(WebModel::class.java)
        bing.webModel = model
        bing.lifecycleOwner = this

        return bing.root
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            Log.d(TAG, "onPageStarted: ")
            //do you  work
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            Log.d(TAG, "onProgressChanged: $newProgress")
            //do you work
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
    }
}
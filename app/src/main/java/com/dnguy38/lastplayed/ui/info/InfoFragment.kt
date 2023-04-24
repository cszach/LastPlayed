package com.dnguy38.lastplayed.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dnguy38.lastplayed.BuildConfig
import com.dnguy38.lastplayed.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)

        val applicationIdText = binding.applicationIdTextView
        val versionNameText = binding.versionNameTextView
        val versionCodeText = binding.versionCodeTextView
        val buildTypeText = binding.buildTypeTextView

        applicationIdText.text = BuildConfig.APPLICATION_ID
        versionNameText.text = BuildConfig.VERSION_NAME
        versionCodeText.text = BuildConfig.VERSION_CODE.toString()
        buildTypeText.text = BuildConfig.BUILD_TYPE

        return binding.root
    }
}
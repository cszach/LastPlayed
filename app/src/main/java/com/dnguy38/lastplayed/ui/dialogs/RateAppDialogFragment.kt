package com.dnguy38.lastplayed.ui.dialogs

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.dnguy38.lastplayed.databinding.FragmentRateAppDialogBinding

class RateAppDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentRateAppDialogBinding
    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentRateAppDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRateAppDialogBinding.inflate(inflater, container, false)

        val noButton = binding.cancelRatingButton
        val rateButton = binding.rateButton

        noButton.setOnClickListener {
            // TODO: Do something when no is selected
            dismiss()
        }

        rateButton.setOnClickListener {
            preferences.edit().putBoolean("rated", true).apply()
            // TODO: Process rating
            dismiss()
        }

        return binding.root
    }
}
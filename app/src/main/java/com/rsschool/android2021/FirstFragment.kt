package com.rsschool.android2021

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.math.max

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min: EditText? = null
    private var max: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        min = view.findViewById(R.id.min_value)
        max = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            if (check(min, max)) {
                val activity = activity
                val myActivity = activity as? MainActivity
                val minValue = min?.text.toString().toInt()
                val maxValue = max?.text.toString().toInt()
                myActivity?.setMinMax(minValue, maxValue)
            } else {
                Toast.makeText(context, "MIN and MAX should be valid O^O!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun check(min: EditText?, max: EditText?): Boolean {
        if (min?.text.isNullOrBlank() || max?.text.isNullOrBlank()) {
            return false
        }
        val minValue = min?.text.toString().toIntOrNull()
        val maxValue = max?.text.toString().toIntOrNull()
        val result = minValue != null && maxValue != null
                && minValue >= 0 && minValue <= Int.MAX_VALUE
                && maxValue >= 0 && maxValue <= Int.MAX_VALUE
                && minValue < maxValue
        Log.i("FirstFragment", "check(): result = $result")
        return result
    }

    interface GenerateListener {

        fun setMinMax(min: Int, max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}
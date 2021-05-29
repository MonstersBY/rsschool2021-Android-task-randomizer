package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import java.lang.NumberFormatException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var Interaction:Interaction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        Interaction = context as Interaction
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val min = view.findViewById<EditText>(R.id.min_value)
        val max = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            if(valid(min,max)){
                Interaction.InteractionSecond(min.text.toString().toInt(),max.text.toString().toInt())
            }
            else Toast.makeText(context,"Поля заполнены не корректно", Toast.LENGTH_SHORT).show()
        }
    }

    fun valid (min:EditText, max:EditText): Boolean{
        val minValue = min.text.toString()
        val maxValue = max.text.toString()
        if((minValue.isNotEmpty() && minValue.isDigitsOnly()) && (maxValue.isNotEmpty() && maxValue.isDigitsOnly())){
            try {
                val minInt = minValue.toInt()
                val maxInt = maxValue.toInt()
                if (minInt < maxInt && maxInt > 0 && minInt >= 0 && maxInt > 0) {
                    return true
                }
            }catch (e: NumberFormatException){
                return false
            }

        }
        return false

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
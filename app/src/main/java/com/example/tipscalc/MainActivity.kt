package com.example.tipscalc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipscalc.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            calculateButton.setOnClickListener {
                calculateTip()
            }
            costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
                handleKeyEvent(view, keyCode)
            }
        }
    }

    fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipsSum.text = ""
            return
        }

        val tipPercentage = when (binding.tipsOptions.checkedRadioButtonId) {
            R.id.option_20 -> 0.20
            R.id.option_15 -> 0.15
            else -> 0.10
        }

        var tip = tipPercentage * cost

        if (binding.roundTips.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipsSum.text = getString(R.string.tips, formattedTip)

    }

    /* спрятать клавиатуру после нажатия ОК */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
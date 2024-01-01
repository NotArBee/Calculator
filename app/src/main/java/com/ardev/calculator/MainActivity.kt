package com.ardev.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    private var tvResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tv_result)
    }

    fun onDigit(view: View) {
        tvResult?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvResult?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvResult?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvResult?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvResult?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvResult?.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = onRemoveZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = onRemoveZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = onRemoveZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (tvValue.contains("X")) {
                    val splitValue = tvValue.split("X")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = onRemoveZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun onRemoveZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0, result.length -2)
        }
        return value
    }

    fun onDelete(view: View) {
        val result = tvResult?.text.toString()
        if (result.isNotEmpty()) {
            tvResult?.text = result.substring(0, result.length - 1)
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("%")
                    || value.contains("/")
                    || value.contains("X")
                    || value.contains("-")
                    || value.contains("+")
        }
    }
}
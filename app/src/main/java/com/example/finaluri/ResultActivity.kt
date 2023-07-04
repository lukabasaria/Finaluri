package com.example.finaluri

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_AMOUNT = "extra_amount"
        private const val EXTRA_SOURCE_CURRENCY = "extra_source_currency"
        private const val EXTRA_TARGET_CURRENCY = "extra_target_currency"
        private const val EXTRA_RESULT = "extra_result"

        fun newIntent(
            context: Context,
            amount: Double,
            sourceCurrency: String,
            targetCurrency: String,
            result: Double
        ): Intent {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra(EXTRA_AMOUNT, amount)
            intent.putExtra(EXTRA_SOURCE_CURRENCY, sourceCurrency)
            intent.putExtra(EXTRA_TARGET_CURRENCY, targetCurrency)
            intent.putExtra(EXTRA_RESULT, result)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val amount = intent.getDoubleExtra(EXTRA_AMOUNT, 0.0)
        val sourceCurrency = intent.getStringExtra(EXTRA_SOURCE_CURRENCY)
        val targetCurrency = intent.getStringExtra(EXTRA_TARGET_CURRENCY)
        val result = intent.getDoubleExtra(EXTRA_RESULT, 0.0)

        val amountTextView = findViewById<TextView>(R.id.amountTextView)
        val sourceCurrencyTextView = findViewById<TextView>(R.id.sourceCurrencyTextView)
        val targetCurrencyTextView = findViewById<TextView>(R.id.targetCurrencyTextView)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        amountTextView.text = "Сумма: $amount"
        sourceCurrencyTextView.text = "Исходная валюта: $sourceCurrency"
        targetCurrencyTextView.text = "Целевая валюта: $targetCurrency"
        resultTextView.text = "Результат: $result"
    }
}

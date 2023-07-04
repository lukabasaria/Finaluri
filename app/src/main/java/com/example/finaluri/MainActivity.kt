import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var sourceCurrencySpinner: Spinner
    private lateinit var targetCurrencySpinner: Spinner
    private lateinit var convertButton: Button

    private val currencies = arrayOf("USD", "EUR", "GBP", "JPY") // Пример списка валют

    private val apiUrl = "https://v6.exchangerate-api.com/v6/a059737518831940ee3aaeae/latest/USD" // Замените YOUR_API_KEY на ваш API-ключ

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        amountEditText = findViewById(R.id.amountEditText)
        sourceCurrencySpinner = findViewById(R.id.sourceCurrencySpinner)
        targetCurrencySpinner = findViewById(R.id.targetCurrencySpinner)
        convertButton = findViewById(R.id.convertButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sourceCurrencySpinner.adapter = adapter
        targetCurrencySpinner.adapter = adapter

        convertButton.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val amount = amountEditText.text.toString().toDouble()
        val sourceCurrency = sourceCurrencySpinner.selectedItem.toString()
        val targetCurrency = targetCurrencySpinner.selectedItem.toString()

        val url = apiUrl.replace("USD", sourceCurrency)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val rates = response.getJSONObject("conversion_rates")
                val exchangeRate = rates.getDouble(targetCurrency)

                val result = amount * exchangeRate

                val intent = ResultActivity.newIntent(this, amount, sourceCurrency, targetCurrency, result)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Ошибка при получении курса валют", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}

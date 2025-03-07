package com.ferhat.weightonotherplanets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.io.InvalidObjectException

class MainActivity : AppCompatActivity() {
    val mapMultsPlanets = mapOf(
        "MERCURY" to 0.38,
        "VENUS" to 0.91,
        "EARTH" to 1.0,
        "MARS" to 0.38,
        "JUPITER" to 2.34,
        "SATURN" to 1.06,
        "URANUS" to 0.92,
        "NEPTUNE" to 1.19
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etWorldWeight : EditText? = findViewById(R.id.etWWeight) as? EditText
        val btnCalculate : Button? = findViewById(R.id.btnCalculate) as? Button


        if (etWorldWeight == null || btnCalculate == null)
            throw InvalidObjectException("EditText or Button objects are null")
        btnCalculate.setOnClickListener { view ->
            val worldWeight = when (etWorldWeight.text.toString()) {
                "" -> 0
                else -> Integer.valueOf(etWorldWeight.text.toString())
            }
            val planetName = getSelectedPlanetName()
            var result = "N/A"
            if (planetName != "N/A")
                result = calcWeight(worldWeight, planetName).toString()
            Snackbar.make(view, result, Snackbar.LENGTH_LONG).show()
        }
    }

    fun getSelectedPlanetName() : String {
        val id = findViewById<RadioGroup>(R.id.rgrpPlanets).checkedRadioButtonId
        val planetName : String
        if (id != -1)
            planetName = findViewById<RadioButton>(id).text.toString()
        else
            planetName = "N/A"
        return planetName.uppercase()
    }

    fun calcWeight(weightWorld : Int, planetName : String) = mapMultsPlanets[planetName]!!*weightWorld
}
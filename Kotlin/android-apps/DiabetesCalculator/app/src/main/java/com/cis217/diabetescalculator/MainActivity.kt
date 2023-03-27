package com.cis217.diabetescalculator

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //Create variables to use throughout the program
    lateinit var btnCalcA1C: Button
    lateinit var btnCalcEAG: Button
    lateinit var edteAG: EditText
    lateinit var edtA1c: EditText
    lateinit var rdoADAG: RadioButton
    lateinit var rdoDCCT: RadioButton
    lateinit var result: TextView
    private var firstCalc = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Call function to add data to the variables
        initApplication()
    }

    private fun initApplication() {
        btnCalcA1C = findViewById(R.id.btnCalcA1C)
        btnCalcEAG = findViewById(R.id.btnCalcEAG)
        edteAG = findViewById(R.id.edteAG)
        edtA1c = findViewById(R.id.edtA1c)
        rdoADAG = findViewById(R.id.rdoADAG)
        rdoDCCT = findViewById(R.id.rdoDCCT)
        result = findViewById(R.id.result)

        //Call function to watch for changes such as text field changing
        onChangeListener()
    }

    fun eAGOnClick(v: View) {
        //Get a1c text field data and convert it to a double or null
        var a1c = edtA1c.text.toString().toDoubleOrNull()

        //if a1c is null give it a value to prevent errors
        if (a1c == null) {
            a1c = 0.0
        }

        //determine what radio button is selected that call that calculate function
        if (rdoADAG.isChecked) {
            result.text = calculateADAG(null, a1c)
        }

        if (rdoDCCT.isChecked) {
            result.text = calculateDCCT(null, a1c)
        }

        //set first calc to true
        firstCalc = true
    }

    fun a1cOnClick(v: View) {
        //Get eAG text field data and convert it to a integer or null
        var eAG = edteAG.text.toString().toIntOrNull()

        //if eAG is null give it a value to prevent errors
        if (eAG == null) {
            eAG = 0
        }

        //determine what radio button is selected that call that calculate function
        if (rdoADAG.isChecked) {
            result.text = calculateADAG(eAG, null)
        }

        if (rdoDCCT.isChecked) {
            result.text = calculateDCCT(eAG, null)
        }

        //set first calc to true
        firstCalc = true
    }

    fun radioButtonOnClick(v: View) {

        //check if a calculate button has been press yet. If so, recalculate with different formula
        if (firstCalc) {
            if (rdoDCCT.isChecked && edteAG.text.toString() != "" || rdoADAG.isChecked && edteAG.text.toString() != "") {
                btnCalcA1C.performClick()
            } else if (rdoDCCT.isChecked && edtA1c.text.toString() != "" || rdoADAG.isChecked && edtA1c.text.toString() != "") {
                btnCalcEAG.performClick()
            }
        }
    }

    private fun calculateADAG(eAG: Int?, a1c: Double?): String {

        //Check eAG and a1c values to see what formula to use
        val retString = if (eAG == null) {
            val fmResult = (28.7 * a1c!!) - 46.7
            "${fmResult.toInt()}\n(Avg. blood sugar using ADAG)"
        } else {
            val fmResult = (46.7 + eAG) / 28.7
            "${"%.1f".format(fmResult)}\n(A1c using ADAG formula)"
        }

        return retString
    }

    private fun calculateDCCT(eAG: Int?, a1c: Double?): String {

        //Check eAG and a1c values to see what formula to use
        val retString = if (eAG == null) {
            val fmResult = (a1c!! * 35.6) - 77.3
            "${fmResult.toInt()}\n(Avg. blood sugar using DCCT)"
        } else {
            val fmResult = (77.3 + eAG) / 35.6
            "${"%.1f".format(fmResult)}\n(A1c using DCCT formula)"
        }

        return retString
    }

    private fun onChangeListener() {

        //Watch text fields to disable buttons
        //Ask Dave about a better solution to solve this during next QA
        edteAG.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                unSetA1C(edtA1c)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })


        edtA1c.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                unSetEAG(edteAG)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    fun unSetEAG(v: View) {
        //Check to see if data is already empty to prevent the deletion of new data
        if (edtA1c.text.toString() != "") {
            edteAG.text.clear()
        }

        //Disable button
        btnCalcA1C.isClickable = false
        btnCalcA1C.setBackgroundColor(Color.GRAY)

        //Enable button
        //Color "#FF6200EE" found in the colors.xml file
        btnCalcEAG.setBackgroundColor(Color.parseColor("#FF6200EE"))
        btnCalcEAG.isClickable = true

        //reset first calc
        firstCalc = false
    }

    fun unSetA1C(v: View) {
        //Check to see if data is already empty to prevent the deletion of new data
        if (edteAG.text.toString() != "") {
            edtA1c.text.clear()
        }

        //Disable button
        btnCalcEAG.isClickable = false
        btnCalcEAG.setBackgroundColor(Color.GRAY)

        //Enable button
        //Color "#FF6200EE" found in the colors.xml file
        btnCalcA1C.setBackgroundColor(Color.parseColor("#FF6200EE"))
        btnCalcA1C.isClickable = true

        //reset first calc
        firstCalc = false
    }
}
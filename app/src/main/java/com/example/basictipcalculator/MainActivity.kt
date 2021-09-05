package com.example.basictipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basictipcalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    // It's a promise that your code will initialize the variable before using it.
    // If you don't, your app will crash.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        * This line initializes the binding object which
        * you'll use to access Views in the activity_main.xml layout
        * */
        binding = ActivityMainBinding.inflate(layoutInflater)

        /*
        * Set the content view of the activity.
        * Instead of passing the resource ID of the layout,
        * R.layout.activity_main,
        * this specifies the root of the hierarchy
        * of views in your app, binding.root
        * */
        setContentView(binding.root)

        /*
        * Now when you need a reference to a View in your app,
        * you can get it from the binding object
        * instead of calling findViewById().
        * The binding object automatically defines
        * references for every View in your app that has an ID.
        * Using view binding is so much more concise
        * that often you won't even need to create a variable
        * to hold the reference for a View,
        * just use it directly from the binding object.
        * */

        /*
        // Old way with findViewById()
        val myButton: Button = findViewById(R.id.my_button)
        myButton.text = "A button"

        // Better way with view binding
        val myButton: Button = binding.myButton
        myButton.text = "A button"

        // Best way with view binding and no extra variable
        binding.myButton.text = "A button"
        */

        binding.calculateButton.setOnClickListener{ calculateTip() }
        binding.buttonClear.setOnClickListener{ clearInput() }
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun clearInput() {
        // Clear the Cost Of Service
        binding.costOfService.text.clear()
        // Clear the input
        binding.tipResult.text = ""
    }

    /*
    * private means the method or variable is only visible to code within that class,
    * in this case, MainActivity class.
    * There's no reason for code outside MainActivity to call calculateTip(),
    * so you can safely make it private.
    * */
    private fun calculateTip() {
        /*
        * Notice the .text on the end.
        * The first part, binding.costOfService,
        * references the UI element for the cost of service.
        * Adding .text on the end says to take that result
        * (an EditText object), and get the text property from it.
        * This is known as chaining, and is a very common pattern in Kotlin.
        * */
        val stringInTextField = binding.costOfService.text.toString()

        if (stringInTextField.isEmpty()) {
            Snackbar.make(
                binding.root,
                "You have to type a value!",
                Snackbar.LENGTH_SHORT
            ).show()
            displayTip(0.0)
            return
        }

        // To use a decimal number in your app,
        // use the data type called Double instead of Int
        val cost = stringInTextField.toDouble()

        /*
        * ADDITIONAL EMPTY STRING AVOIDANCE
        *
        * val cost = stringInTextField.toDoubleOrNull()
        * if (cost == null) {
        *   return
        * }
        *
        * */

        // Get the tip percentage
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost

        // you can check the isChecked attribute to see if the switch is "on"

        /*
        * The term rounding means adjusting a decimal number
        * up or down to the closest integer value,
        * but in this case, you only want to round up, or find the ceiling
        * */
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }
}

/*
* Summary
    View binding lets you more easily write code that interacts with the UI elements in your app
    The Double data type in Kotlin can store a decimal number.
    Use the checkedRadioButtonId attribute of a RadioGroup to find which RadioButton is selected.
    Use NumberFormat.getCurrencyInstance() to get a formatter to use for formatting numbers as currency.
    You can use string parameters like %s to create dynamic strings that can still be easily translated into other languages.
    Testing is important!
    You can use Logcat in Android Studio to troubleshoot problems like the app crashing.
    A stack trace shows a list of methods that were called. This can be useful if the code generates an exception.
    Exceptions indicate a problem that code didn't expect.
    Null means "no value."
    Not all code can handle null values, so be careful using it.
    Use Analyze > Inspect Code for suggestions to improve your code.
* */

/*
*
* Sometimes all you need is clean and rebuild the project.

Build -> Clean Project
Build -> Rebuild Project

This would be enough for generating the binding classes.
* */
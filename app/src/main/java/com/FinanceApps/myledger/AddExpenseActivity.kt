package com.FinanceApps.myledger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var typeSpinner: Spinner
    private lateinit var categorySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Initialize UI components
        typeSpinner = findViewById(R.id.typeSpinner)
        categorySpinner = findViewById(R.id.categorySpinner)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Populate type spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.type_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        // Populate category spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.category_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        // Set click listener for save button
        saveButton.setOnClickListener {
            // Get selected type and category
            val selectedTypeText = typeSpinner.selectedItem.toString()
            val selectedCategory = categorySpinner.selectedItem.toString()
            val selectedType = if (selectedTypeText.equals("refundable", ignoreCase = true))
                ExpenseType.REFUNDABLE
            else
                ExpenseType.NON_REFUNDABLE
            // You can also get other details like description, amount, etc. from EditText fields

            // Create an Expense object and pass it back to the MainActivity
            val newExpense = Expense(
                type = selectedType,
                category = selectedCategory,
                description = "", // Get the description from EditText
                amount = 0.0 // Get the amount from EditText
            )

            val resultIntent = Intent().apply {
                putExtra("addedExpense", newExpense)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Set click listener for cancel button
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}

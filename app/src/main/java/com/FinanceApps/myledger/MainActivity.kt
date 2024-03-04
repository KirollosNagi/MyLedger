package com.FinanceApps.myledger

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var balance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize balance display
        val balanceTextView: TextView = findViewById(R.id.balanceTextView)
        updateBalanceDisplay(balanceTextView)

        // Add Expense Button
        val addExpenseButton: Button = findViewById(R.id.addExpenseButton)
        addExpenseButton.setOnClickListener {
            // Navigate to AddExpenseActivity when the button is clicked
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivityForResult(intent, ADD_EXPENSE_REQUEST)
        }

    }

    private fun updateBalanceDisplay(balanceTextView: TextView) {
        balanceTextView.text = "Balance: $$balance"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_EXPENSE_REQUEST && resultCode == RESULT_OK) {
            // Update balance if needed based on the added expense
            val addedExpense = data?.getParcelableExtra<Expense>("addedExpense")
            addedExpense?.let {
                if (it.type == ExpenseType.REFUNDABLE) {
                    // Adjust balance with the percentage if it's a refundable expense
                    val percentage = it.percentage ?: 0.0
                    balance += it.amount * (percentage / 100)
                } else {
                    // Non-refundable expense
                    balance -= it.amount
                }
                updateBalanceDisplay(findViewById(R.id.balanceTextView))
            }
        }
    }
    companion object {
        private const val ADD_EXPENSE_REQUEST = 1
    }
}
package com.FinanceApps.myledger

// Expense.kt

import android.os.Parcel
import android.os.Parcelable
import java.util.Currency
import java.util.Date

data class Expense(
    val type: ExpenseType,
    val category: String,
    val description: String,
    val amount: Double,
    val currency: Currency,
    val date: Date,
    val refundPercentage: Double?,
    val refundCurrency: Currency?,
    val exchangeRate: Double?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        type = ExpenseType.valueOf(parcel.readString() ?: ""),
        category = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        amount = parcel.readDouble(),
        currency = Currency.getInstance(parcel.readString() ?: ""),
        date = Date(parcel.readLong()),
        refundPercentage = parcel.readValue(Double::class.java.classLoader) as? Double,
        refundCurrency = Currency.getInstance(parcel.readString() ?: ""),
        exchangeRate = parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type.name)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeDouble(amount)
        parcel.writeString(currency.currencyCode)
        parcel.writeLong(date.time)
        parcel.writeValue(refundPercentage)
        parcel.writeString(refundCurrency?.currencyCode)
        parcel.writeValue(exchangeRate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}
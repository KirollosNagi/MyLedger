package com.FinanceApps.myledger

// Expense.kt

import android.os.Parcel
import android.os.Parcelable

data class Expense(
    val type: ExpenseType,
    val category: String,
    val description: String,
    val amount: Double,
    val percentage: Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        type = parcel.readSerializable() as ExpenseType,
        category = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        amount = parcel.readDouble(),
        percentage = parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(type)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeDouble(amount)
        parcel.writeValue(percentage)
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
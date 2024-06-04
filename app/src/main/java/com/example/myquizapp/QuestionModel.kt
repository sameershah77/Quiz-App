package com.example.myquizapp
import android.os.Parcel
import android.os.Parcelable

data class QuestionModel(
    val question: String,
    val answer: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String
) : Parcelable {

    // Constructor for parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Write object data to the parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeString(option1)
        parcel.writeString(option2)
        parcel.writeString(option3)
        parcel.writeString(option4)
    }

    // Describe the kinds of special objects contained in this Parcelable instance
    override fun describeContents(): Int {
        return 0
    }

    // Companion object to create instances of your Parcelable class from a Parcel
    companion object CREATOR : Parcelable.Creator<QuestionModel> {
        override fun createFromParcel(parcel: Parcel): QuestionModel {
            return QuestionModel(parcel)
        }

        override fun newArray(size: Int): Array<QuestionModel?> {
            return arrayOfNulls(size)
        }
    }
}

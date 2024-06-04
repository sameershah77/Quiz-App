package com.example.myquizapp




class Subject {
    var category_img = 0
    var category_text = ""
    lateinit var innerArray :  ArrayList<QuestionModel>

    constructor(category_img: Int, category_text: String, innerArray: ArrayList<QuestionModel>) {
        this.category_img = category_img
        this.category_text = category_text
        this.innerArray = innerArray
    }
}


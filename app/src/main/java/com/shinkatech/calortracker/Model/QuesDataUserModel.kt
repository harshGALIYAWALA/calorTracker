package com.shinkatech.calortracker.Model

data class QuesDataUserModel(
    val name: String ?= null,
    val age:String ?= null,
    val gender:String ?= null,
    val height:String ?= null,
    val weight:String ?= null,
    val fitnessGoal:String ?= null,
    val targetDate:String ?= null,
    val currentActivityLevel:String ?= null,
    val email:String ?= null,
    val password: String ?= null
)

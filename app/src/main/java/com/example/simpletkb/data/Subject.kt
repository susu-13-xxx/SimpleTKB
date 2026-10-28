package com.example.simpletkb.data

data class Subject(
    val id: String = System.currentTimeMillis().toString(),
    val name: String,
    val room: String,
    val teacher: String,
    val dayOfWeek: String,
    val time: String
)


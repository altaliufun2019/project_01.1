package com.example.project_1.Constants

import java.text.SimpleDateFormat

object Constants {
    val FORMATTER = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    object Tasks {
        val FETCH_DATA = 1
        val GET_DATA = 2
        val REFRESH_DATA = 3
        val CLEAR_DATA = 4
    }
}
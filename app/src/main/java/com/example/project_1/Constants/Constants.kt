package com.example.project_1.Constants

import android.content.Context
import java.text.SimpleDateFormat

object Constants {
    val FORMATTER = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    object Tasks {
        const val FETCH_DATA = 1
        const val GET_DATA = 2
        const val REFRESH_DATA = 3
        const val CLEAR_DATA = 4
        const val CONNECTION_CHANGE = 5
    }
}
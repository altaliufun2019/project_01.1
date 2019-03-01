package com.example.project_1.Managers

import com.example.project_1.Managers.MessageControllers.DispatchQueue
import com.example.project_1.UIComponents.DataAdapter.DataNumber

object ConnectionManager {
    val mQueue: DispatchQueue = DispatchQueue("cloud")

    fun load(): List<DataNumber>{
        //TODO
        return emptyList()
    }
}
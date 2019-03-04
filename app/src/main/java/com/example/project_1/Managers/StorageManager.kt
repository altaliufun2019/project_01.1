package com.example.project_1.Managers

import android.app.Application
import android.content.ContentProvider
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.example.project_1.Constants.Constants

import com.example.project_1.Managers.MessageControllers.DispatchQueue
import com.example.project_1.UIComponents.DataAdapter.DataNumber
import java.io.*
import java.util.*

class StorageManager private constructor() {
    private val mQueue = DispatchQueue("storage")
    private val mainHandler = Handler(Looper.getMainLooper())

    private lateinit var context: Context
    fun save(new_data: ArrayList<DataNumber>) {

        context.openFileOutput("chc", Context.MODE_PRIVATE).use { DataOutputStream(it).writeInt(new_data.last().id) }

    }

    fun load() {
        mQueue.post(Runnable {

            var size: Int = 0
            val arrayList = ArrayList<DataNumber>()
            context.openFileInput("chc").use { size = DataInputStream(it).readInt() }
            for (i in 1..size) {
                arrayList.add(DataNumber(i, Date()))

            }
            mainHandler.post {
                MessageController.onTransactionComplete(arrayList, Constants.Tasks.REFRESH_DATA)
            }


        })
    }

    companion object {
        val instance = StorageManager()
    }

    fun setContext(context: Context) {
        this.context = context

    }
}

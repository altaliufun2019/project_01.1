package com.example.project_1.Managers

import android.os.Handler
import android.os.Looper
import com.example.project_1.Constants.Constants
import com.example.project_1.Managers.MessageControllers.DispatchQueue
import com.example.project_1.UIComponents.DataAdapter.DataNumber
import java.util.*

object ConnectionManager {
    private val mQueue: DispatchQueue = DispatchQueue("cloud")
    private val mainHandler = Handler(Looper.getMainLooper())

    fun load(n: Int){
        mQueue.post( Runnable {
            println("${Thread.currentThread().name} completed task")
            MessageController.runningTransactions++
            val response = _load(n)
            mainHandler.post{
                MessageController.onTransactionComplete(response, Constants.Tasks.GET_DATA)
            }
        })
    }

    private fun _load(n: Int): List<DataNumber> {
        while (MessageController.runningTransactions != 1){
        }
        val n = MessageController.lastData

        Thread.sleep(100)
        return List(10){ index -> DataNumber(index + n + 1, Date()) }
    }
}
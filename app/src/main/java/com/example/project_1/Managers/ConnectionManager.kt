package com.example.project_1.Managers

import android.os.Handler
import android.os.Looper
import com.example.project_1.Constants.Constants
import com.example.project_1.Managers.MessageControllers.DispatchQueue
import com.example.project_1.UIComponents.DataAdapter.DataNumber
import java.util.*

object ConnectionManager {
    val mQueue: DispatchQueue = DispatchQueue("cloud")
    val mainHandler = Handler(Looper.getMainLooper())

    fun load(n: Int){
        mQueue.post( Runnable {
            MessageController.runningTransactions++
            val response = _load(n)
            mainHandler.post{
                MessageController.onTransactionComplete(response, Constants.Tasks.GET_DATA)
            }
        })
    }

    private fun _load(n: Int): List<DataNumber> {
        // TODO: for similar functionality to project's main idea, needs to be commented
        while (MessageController.runningTransactions != 1){
        }
        val n = MessageController.lastData

        Thread.sleep(500)
        val new_list = List(10){index -> DataNumber(index + n + 1, Date()) }
        return new_list
    }
}
package com.example.project_1.Managers

import com.example.project_1.Constants.Constants
import com.example.project_1.UIComponents.DataAdapter.DataNumber
import com.example.project_1.UIComponents.DataAdapter.DataNumberAdapter
import java.util.ArrayList

object MessageController {
    lateinit var mAdapter: DataNumberAdapter
    val mData: MutableList<DataNumber> = emptyList<DataNumber>().toMutableList()
    get() = if (MessageController.isCleared) emptyList<DataNumber>().toMutableList() else field

    private var isCleared = false
    var lastData = 0
    var runningTransactions: Int = 0

    fun clear() {
        isCleared = true
        mAdapter.mData = emptyList()
        mAdapter.notifyDataSetChanged()
    }

    fun changeData() {
        mAdapter.mData = mData
        mAdapter.notifyDataSetChanged()
    }

    fun fetch(fromCache: Boolean) {
        isCleared = false
        var addedData: List<DataNumber>
        if (fromCache) {
            StorageManager.instance.load()
        }
        else {
            ConnectionManager.load(lastData)
        }
    }

    fun onTransactionComplete(newList: List<DataNumber>, taskID: Int) {

        when(taskID) {
            Constants.Tasks.GET_DATA -> StorageManager.instance.save(newList as ArrayList<DataNumber>)
            Constants.Tasks.REFRESH_DATA -> mData.clear()
        }
        mData.addAll(newList)
        lastData = mData.last().id
        runningTransactions--
        println("${Thread.currentThread().name}: completed task [$taskID]")
        NotificationCenter.getInstance().data_loaded()
    }
}
package com.example.project_1.Managers

import com.example.project_1.Constants.Constants
import com.example.project_1.UIComponents.DataAdapter.DataNumber
import com.example.project_1.UIComponents.DataAdapter.DataNumberAdapter
import java.util.ArrayList

object MessageController {
    lateinit var mAdapter: DataNumberAdapter
    // why exactly do we have this??
    val mData: MutableList<DataNumber> = emptyList<DataNumber>().toMutableList()

    fun clear() {
        mAdapter.mData = emptyList()
        mAdapter.notifyDataSetChanged()
    }

    fun changeData() {
        mAdapter.mData = mData
        mAdapter.notifyDataSetChanged()
    }

    fun fetch(fromCache: Boolean) {
        var addedData: List<DataNumber>
        if (fromCache) {
            StorageManager.getInstance().load()
        }
        else {
            ConnectionManager.load(if(!mData.isEmpty()) mData.last().number else 0 )
        }
    }

    fun onTransactionComplete(newList: List<DataNumber>, taskID: Int) {
        println("${Thread.currentThread().name}: completed task [$taskID]")

        mData.addAll(newList)
        when(taskID) {
            Constants.Tasks.GET_DATA -> StorageManager.getInstance().save(newList as ArrayList<DataNumber>)
        }
        NotificationCenter.getInstance().data_loaded()
    }
}
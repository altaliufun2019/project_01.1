package com.example.project_1.Managers

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

    fun fetch(fromCache: Boolean) {
        var addedData: List<DataNumber>
        if (fromCache) {
            addedData = StorageManager.getInstance().load()
        }
        else {
            addedData = ConnectionManager.load()
            StorageManager.getInstance().save(addedData as ArrayList<DataNumber>)
        }
        mData.addAll(addedData)
        NotificationCenter.getInstance().data_loaded()


    }
}
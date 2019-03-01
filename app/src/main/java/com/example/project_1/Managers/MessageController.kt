package com.example.project_1.Managers

import com.example.project_1.UIComponents.DataAdapter.DataNumberAdapter

object MessageController {
    lateinit var mAdapter: DataNumberAdapter

    fun clear() {
        mAdapter.mData = emptyList()
        mAdapter.notifyDataSetChanged()
    }

    fun get() {

    }

    fun refresh() {

    }
}
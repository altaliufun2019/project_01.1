package com.example.project_1

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
package com.example.project_1.Managers.MessageControllers

import android.os.Handler
import android.os.Looper
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class DispatchQueue(mThreadName: String): Thread() {
    private lateinit var handler: Handler
    private val latch: CountDownLatch = CountDownLatch(1)

    init {
        name = mThreadName
        start()
    }

    fun post(job: Runnable) {
        try{
            latch.await()
            handler.post(job)
        } catch (e: Exception){
        }
    }

    override fun run() {
        Looper.prepare()
        handler = Handler()
        latch.countDown()
        Looper.loop()
    }
}
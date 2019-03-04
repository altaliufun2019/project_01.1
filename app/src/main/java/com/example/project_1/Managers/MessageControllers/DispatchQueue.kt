package com.example.project_1.Managers.MessageControllers

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class DispatchQueue(mThreadName: String): Thread() {
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
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
            println("problem")
        }
    }

    override fun run() {
        handlerThread = HandlerThread(name)
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        latch.countDown()
    }
}
package com.example.project_1.Managers.MessageControllers

import android.os.Handler
import android.os.Looper
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class DispatchQueue(mThreadName: String): Thread() {
    lateinit var handler: Handler
    val latch: CountDownLatch = CountDownLatch(1)

    init {
        name = mThreadName
        start()
    }

    fun post(message: Message) {
        try{
            latch.await()
            handler.post(message.job)
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
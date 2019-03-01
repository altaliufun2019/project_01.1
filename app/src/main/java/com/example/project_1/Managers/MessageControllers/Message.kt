package com.example.project_1.Managers.MessageControllers

class Message(val name: String, val job: Runnable, val desc: String = "") {
    fun doJob() {
        job.run()
    }
}
package com.mumz.test.disruptor.consumer.kotlin

import com.lmax.disruptor.EventHandler
import com.mumz.learn.disruptor.kotlin.pojo.Response

class ResponseConsumer : EventHandler<Response> {
    val responseMap = mutableMapOf<Long, Response>()

    override fun onEvent(event: Response, sequence: Long, endOfBatch: Boolean) {
        val response = responseMap[event.id]
        response?.enrich(event.id, event.name, event.response)
        if (response?.complete == true) responseMap.remove(event.id)
        else responseMap[event.id] = event
    }
}
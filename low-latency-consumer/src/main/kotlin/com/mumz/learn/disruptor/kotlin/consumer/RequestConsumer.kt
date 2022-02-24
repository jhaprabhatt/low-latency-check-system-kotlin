package com.mumz.test.disruptor.consumer.kotlin

import com.lmax.disruptor.EventHandler
import com.lmax.disruptor.RingBuffer
import com.mumz.learn.disruptor.kotlin.pojo.Request
import com.mumz.learn.disruptor.kotlin.pojo.Response

class RequestConsumer(val responseRingBuffer: RingBuffer<Response>) :
    EventHandler<Request> {
    override fun onEvent(event: Request, sequence: Long, endOfBatch: Boolean) {
        produce(
            event.id, event.name, when {
                endOfBatch -> "End of Batch"
                else -> "Before Batch"
            }
        )
    }

    private fun produce(id: Long, name: CharSequence, resp: CharSequence) {
        val seq = responseRingBuffer.next()
        try {
            responseRingBuffer.get(seq).enrich(id, name, resp)
        } finally {
            responseRingBuffer.publish(seq)
        }
    }
}
package com.mumz.learn.disruptor.kotlin.service

import com.lmax.disruptor.YieldingWaitStrategy
import com.lmax.disruptor.dsl.Disruptor
import com.lmax.disruptor.dsl.ProducerType
import com.lmax.disruptor.RingBuffer
import com.mumz.learn.disruptor.kotlin.factory.RequestFactory
import com.mumz.learn.disruptor.kotlin.factory.ResponseFactory
import com.mumz.learn.disruptor.kotlin.pojo.Request
import com.mumz.learn.disruptor.kotlin.pojo.Response
import com.mumz.test.disruptor.consumer.kotlin.RequestConsumer
import com.mumz.test.disruptor.consumer.kotlin.ResponseConsumer
import java.util.concurrent.ThreadFactory

class LowLatencyService {
    val bufferSize = 1024

    fun startResponseDisruptor(bufferSize: Int): Disruptor<Response> {
        println("Starting Response Disruptor")
        val responseDisruptor = Disruptor(
            ResponseFactory(),
            bufferSize,
            ThreadFactory { r -> Thread(r, "Response Processor Thread") },
            ProducerType.MULTI,
            YieldingWaitStrategy()
        )
        responseDisruptor.handleEventsWith(ResponseConsumer())
        responseDisruptor.start()
        println("Started Response Disruptor")
        return responseDisruptor
    }

    fun startRequestDisruptor(bufferSize: Int, responseRingBuffer: RingBuffer<Response>): Disruptor<Request> {
        println("Starting Request Disruptor")
        val requestDisruptor = Disruptor(
            RequestFactory(),
            bufferSize,
            ThreadFactory { r -> Thread(r, "Request Processor Thread") },
            ProducerType.MULTI,
            YieldingWaitStrategy()
        )
        requestDisruptor.handleEventsWith(RequestConsumer(responseRingBuffer))
        requestDisruptor.start()
        println("Started Request Disruptor")
        return requestDisruptor
    }
}

fun main() {
    val lowLatencyService = LowLatencyService()
    val responseDisruptor = lowLatencyService.startResponseDisruptor(lowLatencyService.bufferSize)
    lowLatencyService.startRequestDisruptor(lowLatencyService.bufferSize, responseDisruptor.ringBuffer)
}
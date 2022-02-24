package com.mumz.learn.disruptor.kotlin.consumer

import com.lmax.disruptor.RingBuffer
import com.lmax.disruptor.YieldingWaitStrategy
import com.mumz.learn.disruptor.kotlin.factory.ResponseFactory
import com.mumz.learn.disruptor.kotlin.pojo.Request
import com.mumz.learn.disruptor.kotlin.pojo.Response
import com.mumz.test.disruptor.consumer.kotlin.RequestConsumer
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class RequestConsumerTest {

    val ringBuffer: RingBuffer<Response> =
        RingBuffer.createMultiProducer(ResponseFactory(), 1024, YieldingWaitStrategy())
    val requestConsumer = RequestConsumer(ringBuffer)

    @Test
    fun whenProvidedARingBufferRequestConsumerIsAbleToConsumeAndProduceResponse() {
        requestConsumer.onEvent(Request(1, "A"), 1, false)
        val publishedResponse = ringBuffer.get(0)
        assertEquals(1, publishedResponse.id)
        assertEquals("A", publishedResponse.responses[0])
        assertEquals("Before Batch", publishedResponse.responses[1])
    }
}
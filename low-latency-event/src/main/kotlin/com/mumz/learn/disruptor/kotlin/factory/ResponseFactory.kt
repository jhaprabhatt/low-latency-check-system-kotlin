package com.mumz.learn.disruptor.kotlin.factory

import com.lmax.disruptor.EventFactory
import com.mumz.learn.disruptor.kotlin.pojo.Response

class ResponseFactory: EventFactory<Response> {
    override fun newInstance(): Response {
        return Response()
    }
}
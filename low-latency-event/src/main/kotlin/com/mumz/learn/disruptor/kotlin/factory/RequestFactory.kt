package com.mumz.learn.disruptor.kotlin.factory

import com.lmax.disruptor.EventFactory
import com.mumz.learn.disruptor.kotlin.pojo.Request

class RequestFactory : EventFactory<Request> {
    override fun newInstance(): Request {
        return Request()
    }
}
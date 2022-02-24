package com.mumz.learn.disruptor.kotlin.pojo

class Response(id: Long = 0, name: CharSequence = "", response: CharSequence = "") {

    var id = id
        get() = field

    val name = name
        get() = field

    val response = response
        get() = field

    val responses = Array<CharSequence>(4) { "" }

    var complete = false

    fun enrich(id: Long, target: CharSequence, response: CharSequence) {
        var firstIndex = 2
        var secondIndex = 3

        if (responses[0] == "") {
            firstIndex = 0
            secondIndex = 1
        } else {
            complete = true
        }
        responses[firstIndex] = target
        responses[secondIndex] = response
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Response

        if (id != other.id) return false
        if (name != other.name) return false
        if (response != other.response) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + response.hashCode()
        return result
    }

    override fun toString(): String {
        return "Response(id=$id, name=$name, response=$response, responses=${responses.contentToString()}, complete=$complete)"
    }
}
package com.mumz.learn.disruptor.kotlin.pojo

class Request(id: Long = 0, name: CharSequence = "") {

    var id = id
        get() = field
        set(value) {
            field = value
        }
    var name = name
        get() = field
        set(value) {
            field = value
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Request

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Request(id=$id, name=$name)"
    }
}
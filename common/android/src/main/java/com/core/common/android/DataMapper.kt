package com.core.common.android

import java.util.*

abstract class DataMapper<E, T>() {

    /**
     * Transform a [E] into an [T].
     *
     * @param source Object to be transformed.
     * @return [T] if valid [E] otherwise null.
     */
    abstract fun transform(source: E): T

    /**
     * Transform a List of [E] into a List of [T].
     *
     * @param source Object List to be transformed.
     * @return [T] if valid [E] otherwise null.
     */
    fun transform(source: List<E>): List<T> {
        val destination = ArrayList<T>()
        var out: T?
        for (`in` in source) {
            out = transform(`in`)
            if (out != null) {
                destination.add(out)
            }
        }
        return destination
    }

    /**
     * Transform a [T] into an [E].
     *
     * @param source Object to be transformed.
     * @return [E] if valid [T] otherwise null.
     */
    abstract fun inverse(source: T): E

    /**
     * Transform a List of [T] into a List of [E].
     *
     * @param source Object List to be transformed.
     * @return [E] if valid [T] otherwise null.
     */
    fun inverse(source: List<T>): List<E> {
        val destination = ArrayList<E>()
        var out: E?
        for (`in` in source) {
            out = inverse(`in`)
            if (out != null) {
                destination.add(out)
            }
        }
        return destination
    }

}

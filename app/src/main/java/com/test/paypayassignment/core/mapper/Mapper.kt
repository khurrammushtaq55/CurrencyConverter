package com.test.paypayassignment.core.mapper


interface Mapper<T, E> {
    fun to(t: T): E
}

interface ConverterMapper<T, Y, X, E> {
    fun to(t1: T, t2: Y, t3: X): E
}


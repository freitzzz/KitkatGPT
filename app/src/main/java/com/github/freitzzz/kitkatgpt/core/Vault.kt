package com.github.freitzzz.kitkatgpt.core

import java.lang.reflect.Type
import kotlin.reflect.KProperty

open class Vault<T> {
    val container = mutableMapOf<Type, T>()

    inline fun <reified S: T> store(value: S) {
        container[S::class.java] = value
    }

    inline fun <reified S: T> lookup(): S? = container[S::class.java] as S?
    inline fun <reified S: T> get() = lookup<S>()!!
}

/*open class Vault {
    val container = mutableMapOf<Type, Any>()

    fun store(value: Any) {
        container[value::class.java.superclass::class.java] = value
    }

    inline fun <reified T> lookup(): T? = container[T::class.java] as T?
*//*
    inline fun <reified S: T> get() = lookup<S>()!!
*//*
}*/

class AnyVault: Vault<Any>() {
    inline operator fun<T, reified V: Any> getValue(nothing: T?, property: KProperty<*>): V {
        return vault().get<V>()
    }
}

private val vault = AnyVault()
fun vault() = vault

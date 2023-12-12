package com.example.feedservice.domain

//sealed class Either<out Left, out Right> {
//    internal abstract val isLeft: Boolean
//
//    internal abstract val isRight: Boolean
//
//    abstract fun getOrNull(): Right?
//}
//
//
//data class Left<out A>(val value: A) : Either<A, Nothing>() {
//    override val isLeft = true
//    override val isRight = false
//    override fun getOrNull() = null
//}
//
//data class Right<out B>(val value: B) : Either<Nothing, B>() {
//    override val isLeft = false
//    override val isRight = true
//    override fun getOrNull() = value
//}

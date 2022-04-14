package com.example.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {

    coldFlow()
}

fun coldFlow() {
    runBlocking {
        val dataFlow = getDataByFlow()
        println("Esperando...")
        delay(2000)
        // El flow se ejecuta hasta que se llama su método Collect
        dataFlow.collect{ println(it) }
    }
}

fun getDataByFlow() : Flow<Float> {
    return flow {
        (1..5).forEach {
            println("Procesando Datos...")
            delay(1000)
            emit(20 + it + Random.nextFloat())
        }
    }

}
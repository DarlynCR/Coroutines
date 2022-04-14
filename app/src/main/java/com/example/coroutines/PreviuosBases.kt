package com.example.coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {

    // lambda
    val result = operations(3,2)  { y, x ->  y * x }

    //Corrutinas -> primer ejemplo
    //coroutines()

    // GlobalScope -> Se mantienen durante el ciclo de vida de la App
    // indica el scope - de la corrutina - Main
    globalScope()

    readLine()
    //Usado en testing y en enseñanza, este constructor bloquea el hilo principal
    cRunBloking()
    // Usado para tareas donde no se espere un resultado
    cLaunch() //Retorna un Job
    // Diseñado para cuando esperemos un valor en la corrutina
    cAsync() //Retorna un deferred
    //

}

fun cAsync() {
    runBlocking {
       val result = async {
            startMsg()
            println("Async...")
            endMsg()
            operations(2,5){y,x -> y+5}
        }

        println("Async result = ${result.await()}")
    }
}

fun cRunBloking() {
    runBlocking {
        startMsg()
        println("RunBloking...")
        endMsg()
    }
}

fun cLaunch() {
    runBlocking {
        launch {
            startMsg()
            println("Launch...")
            endMsg()
        }
    }
}

fun globalScope() {

    GlobalScope.launch{
        startMsg()
        println("GlobalScope")
        endMsg()
    }
}

fun coroutines() {
    //Primer ejemplo de corrutina
    runBlocking {
        (1..1_000_000).forEach {
            launch {
                delay(1000)
                print("*")
            }
        }
    }
}

fun startMsg() {
    println("Comenzando corrutina: -${Thread.currentThread().name}-")
}

fun endMsg() {
    println("Corrutina finalizada: -${Thread.currentThread().name}-")
}

//Función de orden superior
fun operations (y : Int , x: Int, block : (Int, Int) -> Int) = block(y,x)


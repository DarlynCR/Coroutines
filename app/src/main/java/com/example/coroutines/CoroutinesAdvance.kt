package com.example.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val cities = listOf("Bucaramanga", "Bogotá", "Medellín", "Cúcuta", "Cartagena")

fun main() {
    // Una forma de trasnferir un flujo de valores entre corrutinas
    basicChannel()
}

fun basicChannel() {
    runBlocking {

        val channel = Channel<String>()

        launch {
            cities.forEach {
                channel.send(it) //método suspend de channel para enviar un valor
            }
        }

        //Primera forma de consumir un canal
   /*     repeat(5){
            println(channel.receive()) //receive capta los valores enviados por el channel
        }

        //Segunda forma de consumir un canal
        for (value in channel){
            println(value)
        }

        //tercera forma de consumir el channel
        channel.consumeEach {
            println(it) }

        //Cuarta forma de consumir el channel
        while(!channel.isClosedForReceive){
            println(channel.receive())
        }*/
        channel.cancel()

        val produce = produce{
            cities.forEach { send(it)  }
        }

        produce.consumeEach { println(it) }
        produce.cancel()
    }
}

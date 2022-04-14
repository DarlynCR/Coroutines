package com.example.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.random.Random

fun main() {

   // coldFlow()

    //cancelFlow()

    //flowOperators()

     terminalFlowOperators()


}

fun terminalFlowOperators() {

    val flow = flowOf(1,2,3,4,5)

    runBlocking {

        val list = getDataByFlow().toList()
        println("list: $list")

        val single = getDataByFlow().take(1).single()
        println("single: $single")

        val first = getDataByFlow().first()
        println("first: $first") // cancela de forma interna el flujo una vez obtenga el 1er valor

        val last = getDataByFlow().last()
        println("last: $last")

        //Retorna un solo valor - para realizar operaciones con los indices
        val reduce = flow.reduce { accumulator, value ->
            println("Acumulador : $accumulator")
            println("Valor : $value")
            accumulator + value
        }

    }
}

fun flowOperators() {

    val flow = flowOf(1,2,3,4,5)

    runBlocking {
        getDataByFlow()
            .filter {
                it < 23
            }
            .map {
                setFormat(it)
            }
           // .collect{
           //     println(it)
           // }

        //Ejemplo de transform
        flow.transform {
            emit(it * 2 ) // cada emit se aplica a cada elemento del flow
            emit(it * 3 )
            emit(it * 4)

        }.collect{ println(it) }

        //Ejemplo de take - solo toma los 3 primeros elementos del flow
        flow.take(3).collect{ println(it) }

    }
}

fun setFormat(temp: Float, degree : String = "C") : String = String.format(Locale.getDefault(),
"%.1f$degree", temp )

fun cancelFlow() {
    runBlocking {
        val job = launch {
            getDataByFlow().collect{ println(it) }
        }
        delay(4000)
        job.cancel() //Se cancela la corrutina y cancela el flow
    }
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
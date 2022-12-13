package server

import db.StarUnix
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

/*
Mi examen fue distinto, pero teniendo en cuenta que lo complique demasiado, creo que
esta version va a ser mas cercana a la que se buscaba en el examen.

-- ENUNCIADO (segun recuerdo) --

R2D2 crea una seccion critica llamada StarUnix, y se pueden enviar tres tipos de mensajes:

    - Por parte de un piloto, envia un registro de una nave
    - Por parte de BB8, busca el numero de misiles totales y el total de naves
    - Por parte de Luke, el quiere el listado de registros/naves al completo.

Nave: Id, tipo de Nave(X-WIND, T-FIGHTER), salto de hiper espacio (boolean), misiles protonicos (entre 10..20), fecha creacion

Tiempo para resolverlo: 2h:30min
------

La solucion que voy a aplicar va a ser muy parecida a la gestion de la clase
    Version Hilos: https://github.com/Mario999X/ClaseServidorHilos
    Version Corrutinas: https://github.com/Mario999X/ClaseServidorCorrutinas

La solucion que aplique en el examen oficial es mas parecido a una
especie de "productor-consumidor", dejo ejemplo:
https://github.com/Mario999X/StarWarsServidorHilos
*/

private const val PUERTO = 6969

fun main() {
    var cliente: Socket

    val starUnix = StarUnix()

    val pool = Executors.newFixedThreadPool(10)
    println("Iniciando servidor...")

    val s = ServerSocket(PUERTO)
    while (true) {
        println("Servidor esperando...")

        cliente = s.accept()
        println("CLiente -> ${cliente.inetAddress} conectado")

        val gc = GestorCliente(cliente, starUnix)
        pool.execute(gc)

        println("Cliente desconectado")
    }
    //s.close()
}
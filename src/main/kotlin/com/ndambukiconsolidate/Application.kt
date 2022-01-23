package com.ndambukiconsolidate

import com.ndambukiconsolidate.plugins.*
import com.ndambukiconsolidate.routes.registerCustomerRoutes
import com.ndambukiconsolidate.routes.registerOrderRoute
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0"){
        configureSecurity()
        configureRouting()
        configureMonitoring()
        configureTemplating()
        configureSerialization()
        registerCustomerRoutes()
        registerOrderRoute()
    }.start(wait = true)
}

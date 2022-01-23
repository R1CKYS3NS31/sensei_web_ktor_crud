package com.ndambukiconsolidate.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.response.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.title

fun Application.configureRouting() {

    routing {
        get("/") {
//                call.respondText("Hello World!")
            call.respondHtml {
                head { title { +"Root" } }
                body {
                    h1 { +"Welcome Home" }
                }
            }
            }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
//        static("static") {
//            resources("static")
////            file("index.html")
////            defaultResource("index.html")
//        }
        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }
        
        }
    }
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

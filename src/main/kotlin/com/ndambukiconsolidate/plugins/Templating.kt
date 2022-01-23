package com.ndambukiconsolidate.plugins

import freemarker.cache.*
import io.ktor.freemarker.*
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.mustache.Mustache
import io.ktor.mustache.MustacheContent
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates/mustache")
    }

    routing {
//        get("/html-freemarker") {
//            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
//        }
//        get("/html-mustache") {
//            call.respond(MustacheContent("index.hbs", mapOf("user" to MustacheUser(1, "user1"))))
//        }
        get("/ricky"){
//            call.respond(MustacheContent("index.hbs", mapOf("user" to MustacheUser(1, "Ricky"))))
            call.respond(MustacheContent("index.hbs", mapOf("entries" to blogEntries[0])))
        }
        get("/sensei"){
//            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries),""))
        }
        get("/nc"){
            call.respondHtml {
                head {
                    title { +"NDAMBUKI CONSOLIDATE" }
                }
                body {
                    h1 { +"NDAMBUKI CONSOLIDATE" }
                    p { +"This is Ndambuki consolidate website" }
                    a("/static/main.html"){
                        +"to Static"
                    }
                }

            }
        }
        get("/rs"){
            val entry= blogEntries[0]
            val headline=entry.headline
            val body=entry.body
            call.respondHtml {
                head {
                    title { +"Ricky Sensei" }
                    link("/static/main.css","stylesheet")
                }
                body {
                    h1 { +"Ricky Journal" }
                    img("ricky imagae","/static/r_sensei.png")
                    p { i { +"Powered by NDAMBUKI CONSOLIDATE" } }
                    hr {  }
                    div {
                        h2 { +"$headline" }
                        p { +"$body" }
                    }
                    hr {  }
                    div {
                        h3 { +"Add a new journal entry" }
                        postForm("/submit",null){
                            input(type = InputType.text,name = "headline")
                            br {  }
                            input(type = InputType.text,name="body")
                            br {  }
                            postButton(null,"submitButton",type = ButtonType.submit){
                                +"Submit button"
                            }
                        }
                    }
                }
            }
        }
        get("/htmldsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }
        post("/submit"){
            val params=call.receiveParameters()
            val headline=params["headline"]?:return@post call.respond(HttpStatusCode.BadRequest)
            val body=params["body"]?:return@post call.respond(HttpStatusCode.BadRequest)
            val newEntry=BlogEntry(headline,body)
            blogEntries.add(0,newEntry)
            call.respondHtml {
                body {
                    h1 {
                        +"Thanks for submitting your entry!"
                    }
                    p {
                        +"We've submitted your new entry titled "
                        b { +newEntry.headline }
                    }
                    p {
                        +"You have submitted a total of ${blogEntries.count()} articles!"
                    }
                    a("/rs"){
                        +"Go back"
                    }
                }
            }
        }
    }
}
val blogEntries= mutableListOf(BlogEntry(
    "The drive to develop!",
    "...it's what keeps me going."
))

data class IndexData(val items: List<Int>)
data class MustacheUser(val id: Int, val name: String)

@Serializable
data class BlogEntry(val headline:String,val body:String)

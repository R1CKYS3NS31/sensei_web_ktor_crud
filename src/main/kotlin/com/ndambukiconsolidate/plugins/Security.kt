package com.ndambukiconsolidate.plugins

import io.ktor.auth.*
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

fun Application.configureSecurity() {
    
    authentication {
    		basic(name = "myauth1") {
    			realm = "Ktor Server"
    			validate { credentials ->
    				if (credentials.name == credentials.password) {
    					UserIdPrincipal(credentials.name)
    				} else {
    					null
    				}
    			}
    		}
    
    	    form(name = "myauth2") {
    	        userParamName = "user"
    	        passwordParamName = "password"
    	        challenge {
    	        	/**/
    			}
    	    }
    	}

    routing {
        authenticate("myauth1") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
        authenticate("myauth1") {
            get("/protected/route/form") {
                val principal = call.principal<UserIdPrincipal>()!!
//                call.respondText("Hello ${principal.name}")
				call.respondHtml {
					head { title { +"RICKY SENSEI" } }
					body { h1 { +"RICKY SENSEI" }
						p { +"Welcome Ricky Sensei" }
						p { +"you logged as ${principal.name}" }
					}
				}
            }
        }
    }
}

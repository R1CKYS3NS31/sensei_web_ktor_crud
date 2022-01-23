package com.ndambukiconsolidate.routes

import com.ndambukiconsolidate.models.Customer
import com.ndambukiconsolidate.models.Customers
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.registerCustomerRoutes(){
//    install(ContentNegotiation){
//        gson {
//        }
//    }
    routing {
        customerRouting()
    }
}
fun Route.customerRouting(){


    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",driver = "org.h2.Driver")
    transaction {
//        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Customers)

        Customers.insert {
            it[firstName] = "Ricky"
            it[lastName] = "Sensei"
            it[email] = "sensei@gmail.com"
        }
        Customers.insert {
            it[firstName] = "Joseh"
            it[lastName] = "Kamau"
            it[email] = "kamau@gmail.com"
        }
        Customers.insert {
            it[firstName] = "Reuben"
            it[lastName] = "Githua"
            it[email] = "githua@gmail.com"
        }
    }
    route("/customer"){
        get {
            val customers = transaction {
                Customers.selectAll().map { Customers.toCustomer(it) }
            }
            call.respondHtml {
                head { title { +"Customers" } }
                body {
                    header { h1 { +"Customers list" } }
                    ol {
                        for (cust in customers){
                            li { +"First Name: ${cust.firstName} , Last Name: ${cust.lastName} , Email: ${cust.email}" }
                        }
                    }
                }
            }
        }
        get("/{id}") {
            val id = call.parameters["id"]
            val customer = transaction {
                Customers.select { Customers.id eq id!!.toInt() }.map { Customers.toCustomer(it) }
            }
            call.respond(customer)
        }
        
        post {
            val customer = call.receive<Customer>()
            transaction {
                Customers.insert {
                    it[firstName] = customer.firstName
                    it[lastName] = customer.lastName
                    it[email] = customer.email
                }
            }
            call.respond(customer)
        }

//        get {
//            if (customerStorage.isNotEmpty()){
//                call.respond(customerStorage)
//            }else{
//                call.respondText("No customers found",status = HttpStatusCode.NotFound)
//            }
//        }
//        get("{id}"){
//            val id =call.parameters["id"]?:return@get call.respondText("Missing or malformed id",status = HttpStatusCode.BadRequest)
//            val customer= customerStorage.find { it.id==id }?:return@get call.respondText("No customer with id $id",status = HttpStatusCode.NotFound)
//            call.respond(customer)
//        }
//        post {
//            val customer=call.receive<Customer>()
//            customerStorage.add(customer)
//            call.respondText("Customer stored correctly",status = HttpStatusCode.Created)
//        }
//        delete("{id}"){
//            val id=call.parameters["id"]?:return@delete call.respond(HttpStatusCode.BadRequest)
//            if (customerStorage.removeIf { it.id==id }){
//                call.respondText("Customer removed correctly",status = HttpStatusCode.Accepted)
//            }else{
//                call.respondText("Not found",status = HttpStatusCode.NotFound)
//            }
//        }
    }
}

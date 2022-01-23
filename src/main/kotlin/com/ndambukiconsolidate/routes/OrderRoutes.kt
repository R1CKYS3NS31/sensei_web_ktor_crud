package com.ndambukiconsolidate.routes

import com.ndambukiconsolidate.models.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

fun Application.registerOrderRoute(){

    authentication {
        basic("orderAuth1"){
            realm="Order Server"
            validate { credentials->
                if (credentials.name== orderPassword.userName && credentials.password== orderPassword.password){
                    UserIdPrincipal(credentials.name)
                }else{
                    null
                }
            }
        }
    }
    routing {
        listOrderRoute()
        authenticate("orderAuth1"){
            getOrderRoute()
        }
        addOrder()
        postAddOrder()
        totalizingOrderRoute()
        deleteOrder()
//        dbOrder()
    }
}
//
fun Route.listOrderRoute(){
    route("/orders"){
        authenticate("orderAuth1"){
            get{
                if (orderStorage.isNotEmpty()){
                    val total= orderStorage
                        .sumOf { order ->
                            order.contents
                                .sumOf { it.amount * it.price }
                        }
                    val totalAmount= orderStorage.sumOf { order ->
                        order.contents.sumOf { it.amount }
                    }
                    val totalPrice= orderStorage.sumOf { order ->
                        order.contents.sumOf { it.price }
                    }
//            call.respond(orderStorage)
                    call.respondHtml {
                        head {
                            title { +"Orders" }
                            link("/static/main.css","stylesheet")
                        }
                        body {
                            div("header"){
                                header {
                                    h1 { +"ORDERS" }
                                }

                            }
                            div (classes = "customer-orders"){
                                h2 { +"All customer Orders" }
                                table {
                                    thead {
                                        tr {
//                                        th { b { +"Delete order" } }
                                            th { b { +tableHeaders.numberData } }
                                            th { b { +tableHeaders.itemData } }
                                            th { b { +tableHeaders.amountData } }
                                            th { b { +tableHeaders.priceData } }
                                            th { b { +tableHeaders.itemsPriceData } }
                                        }
                                        for (orders in orderStorage.sortedBy { it.number }){
                                            for (row in orders.contents){
                                                val itemsPrice=orders.contents.map { it.amount*it.price }
                                                tr{
                                                    td { +orders.number }
                                                    td { +row.item }
                                                    td { +row.amount.toString() }
                                                    td { +row.price.toString() }
                                                    for ( itPrice in itemsPrice){
                                                        td { +"${itPrice.toFloat()}" }
                                                    }
                                                }
                                            }
                                        }
                                        tr {
//                                        td { b { +"" } }
                                            td { b { +tableHeaders.totalData } }
                                            td { b { +"" } }
                                            td { +"$totalAmount" }
                                            td { +"${totalPrice.toFloat()}" }
                                            td { +"${total.toFloat()}" }
                                        }
                                    }
                                }
                            }

                            hr {  }
                            div("delete-order"){
                                postForm("/orders",FormEncType.multipartFormData){
                                    p { +"Order number" }
                                    textInput(name="deleteOrderNumber")
                                    postButton(type = ButtonType.submit){
                                        +"  Delete order"
                                    }
                                }
                            }
                            hr {  }
                            div("add-order"){
                                h2 { +"Add Order" }
                                postForm("/postAddOrder",encType = FormEncType.multipartFormData){
                                    table {
                                        thead {
                                            tr {
                                                th { b { +tableHeaders.numberData } }
                                                th { b { +tableHeaders.itemData } }
                                                th { b { +tableHeaders.amountData } }
                                                th { b { +tableHeaders.priceData } }
                                            }
                                        }
                                        tbody {
                                            tr {
                                                td { textInput(name = "number") }
                                                td { textInput(name = "item") }
                                                td { textInput(name = "amount") }
                                                td { textInput(name = "price") }
                                            }

                                        }
                                    }
                                    postButton(type = ButtonType.submit){
                                        +"Submit order"
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        post {
            val params=call.receiveParameters()
            val deleteOrderNumber=params["deleteOrderNumber"]?:return@post call.respondText("Order number not found",status = HttpStatusCode.NotFound)
            if (orderStorage.removeIf { it.number==deleteOrderNumber }){
                call.respondRedirect("/orders")
            }else{
                call.respondRedirect("/orders")
            }

        }
    }
}
fun Route.getOrderRoute(){
    get("/order/{number}"){
        val number=call.parameters["number"]?:return@get call.respondText("Missing or malformed id",status = HttpStatusCode.BadRequest)
        val order= orderStorage.find { it.number==number}?:return@get call.respondText("No order with number $number",status = HttpStatusCode.NotFound)
//        call.respond(order)
        call.respondHtml {
            head {
                title { +"Order $number" }
                link("/static/main.css","stylesheet")
            }
            body {
                h1 { +"Order $number" }
                table {
                    thead {
                        tr {
                            th { b { +tableHeaders.numberData } }
                            th { b { +tableHeaders.itemData } }
                            th { b { +tableHeaders.amountData } }
                            th { b { +tableHeaders.priceData } }
                            th { b { +tableHeaders.itemsPriceData } }
                        }
                    }
                    tbody {
                        for (row in order.contents){
                            val itemsPrice=order.contents.map { it.amount*it.price }
                            tr {
                                td { +order.number }
                                td { +row.item }
                                td { +"${row.amount}" }
                                td { +"${row.price}" }
                                for (itPrice in itemsPrice){
                                    td { +itPrice.toFloat().toString() }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}
fun Route.postAddOrder(){
    route("/postAddOrder"){
        post {
//            val addOrder=call.receive<Order>()
            val params=call.receiveParameters()
            val number= params["number"] ?:return@post call.respondText("Order number not added",
                status = HttpStatusCode.BadRequest)
            val item=params["Item"]?:return@post call.respondText("Item not added",
                status = HttpStatusCode.BadRequest)
            val amount=params["amount"]?.toIntOrNull()?:return@post call.respondRedirect("/orders")
//            call.respondText("amount not added",status = HttpStatusCode.BadRequest)
            val price=params["price"]?.toDoubleOrNull()?:return@post call.respondRedirect("/orders")
//            call.respondText("price not added",status = HttpStatusCode.BadRequest)
            val contents=OrderItem(item, amount, price)
            val addOrder=Order(number, listOf(contents))

            orderStorage.add(addOrder)
            call.respondRedirect("/orders")
        }
    }
}
fun Route.addOrder(){
    get("/AddOrder"){
        call.respondHtml {
            head {
                title { +"Add order" }
                link("/static/main.css","stylesheet")
            }
            body {
                h1 { +"Add Order" }
                postForm("/postAddOrder",encType = FormEncType.multipartFormData){
                    table {
                        thead {
                            tr {
                                th { b { +tableHeaders.numberData } }
                                th { b { +tableHeaders.itemData } }
                                th { b { +tableHeaders.amountData } }
                                th { b { +tableHeaders.priceData } }
                            }
                        }
                        tbody {
                            tr {
                                td { textInput(name = "number") }
                                td { textInput(name = "item") }
                                td { textInput(name = "amount") }
                                td { textInput(name = "price") }
                                this@table.postButton(type = ButtonType.submit){
                                    +"Submit order"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
fun Route.totalizingOrderRoute(){
    get("/order/{number}/total"){
        val number=call.parameters["number"]?:return@get call.respondText("Missing or malformed id",status = HttpStatusCode.BadRequest)
        val order= orderStorage.find { it.number==number}?:return@get call.respondText("No order with number $number",status = HttpStatusCode.NotFound)
        val total= order.contents.sumOf { it.amount * it.price }
        call.respond(total)
    }
}
fun Route.deleteOrder(){
    route("/deleteOrder"){
        get ("/{number}"){
            val number=call.parameters["number"]?:return@get call.respondText("Unable to delete",status = HttpStatusCode.BadRequest)
            if (orderStorage.removeIf { it.number==number }){
                call.respondText("Order deleted Successfully",status = HttpStatusCode.Accepted)
            }else{
                call.respondText("Unable to delete order",status = HttpStatusCode.BadRequest)
            }
        }
    }
}


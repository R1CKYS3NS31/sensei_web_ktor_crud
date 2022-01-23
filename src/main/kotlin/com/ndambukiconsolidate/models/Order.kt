package com.ndambukiconsolidate.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderPassword(val userName:String,val password: String)

@Serializable
data class TableHeaders(val numberData: String,val itemData: String,val amountData: String,val priceData:String,val itemsPriceData:String,val totalData:String)


@Serializable
data class Order(val number:String,val contents:List<OrderItem>)

@Serializable
data class OrderItem(val item:String,val amount:Int,val price:Double)

//instances
val orderPassword=OrderPassword("sensei","password")

val tableHeaders=TableHeaders(
    "Order Number","Item","Amount of item","Price per item","Order price","Totals"
)
//val orderStorage= mutableListOf<Order>()
//
val orderStorage= mutableListOf(
    Order(
    "14-08-2021", listOf(
        OrderItem("Ham Snadwich",2,5.50),
    )),
    Order(
        "15-08-2021", listOf(
//        OrderItem("Cheeseburger",1,8.50),
//        OrderItem("Water",2,1.50),
//        OrderItem("Coke",2,1.76),
        OrderItem("Ice cream",1,2.35)
    )),
    Order(
        "16-08-2021", listOf(
//        OrderItem("KDF",4,10.50),
//        OrderItem("Ribenna juice",3,50.50),
//        OrderItem("Delmonte Mango juice",1,15.76),
        OrderItem("Mandazi",6,5.35)
    ))
)
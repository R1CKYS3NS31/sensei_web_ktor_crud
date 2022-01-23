package com.ndambukiconsolidate.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

@Serializable
data class Customer(val id:String,val firstName:String,val lastName:String,val email:String)

val customerStorage= mutableListOf<Customer>()

object Customers:Table(){
    fun toCustomer(row: ResultRow):Customer=
        Customer(
            id = row[id].toString(),
            firstName=row[firstName],
            lastName = row[lastName],
            email = row[email]
        )

    val id: Column<Int> = integer("id").autoIncrement()
    val firstName:Column<String> = varchar("first_name",50)
    val lastName:Column<String> = varchar("last_name",50)
    val email:Column<String> = varchar("email",50)
    override val primaryKey = PrimaryKey(id,name = "PK_Customer_ID")
}
package com.ndambukiconsolidate

//fun main(){
////    Database.connect("jdbc:h2:mem:test",driver = "org.h2.Driver")
//    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",driver = "org.h2.Driver")
//    transaction {
//        addLogger(StdOutSqlLogger)
//
//        SchemaUtils.create(Cities)
//
//        val nakuruID = Cities.insert {
//            it[name] = "Nakuru City"
//        } get Cities.id
//
//        print("Cities: ${Cities.selectAll()}")
//    }
//}
//object Cities: IntIdTable(){
//    val name=varchar("name",50)
//}
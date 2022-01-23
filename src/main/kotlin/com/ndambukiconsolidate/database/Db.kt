package com.ndambukiconsolidate.database

import org.jetbrains.exposed.sql.Database

object Db{

   private var config = HikariConfig().apply {
//        jdbcUrl = "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;"
       var dbName="customer"
       jdbcUrl = "jdbc:postgresql://0.0.0.0:5432/$dbName"
//        driverClassName = "org.h2.Driver"
       driverClassName = "org.postgresql.Driver"
//        jdbcUrl = "jdbc:mysql://0.0.0.0:8080"
//        driverClassName = "com.mysql.cj.jdbc.Driver"
       username = "postgres"
       password = "my_password"
       maximumPoolSize = 10
   }
   private val dataSource = HikariDataSource(config)

//    val db= Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
//        val db = Database.connect(dataSource)


//    val db = Database.connect("jdbc:mysql://0.0.0.0:8080", driver = "com.mysql.jdbc.Driver",
//                 user = "root", password = "pass")

//    val db = Database.connect("jdbc:postgresql://localhost:8080/test", driver = "org.postgresql.Driver",
//                 user = "root", password = "your_pwd")

////Gradle
//compile("org.postgresql:postgresql:42.2.2")

//Database.connect("jdbc:mysql://localhost:3306/test", driver = "com.mysql.jdbc.Driver",
//                 user = "root", password = "your_pwd")
////Gradle
//compile("mysql:mysql-connector-java:5.1.48")

//// Database in file, needs full path or relative path starting with ./
//Database.connect("jdbc:h2:./myh2file", "org.h2.Driver")
//// In memory
//Database.connect("jdbc:h2:mem:regular", "org.h2.Driver")
//// In memory / keep alive between connections/transactions
//Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
////Gradle
//compile("com.h2database:h2:1.4.199")
}
package org.maven
import org.apache.spark.sql.SparkSession
import java.util.Properties
import sun.security.krb5.internal.crypto.Des
import org.apache.spark.sql.SaveMode

object mysqlDB {
  def main(args:Array[String])
  {
    val spark = SparkSession
.builder()
.appName("Java Spark SQL basic example")
.config("spark.master", "local")
.getOrCreate();

    import org.apache.log4j.{Level, Logger}   
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)   
    
  val url="jdbc:mysql://localhost:3306/person"
  val table = "employees"
  val properties = new Properties()
  properties.put("user","root")
  properties.put("password","DBpassword")
  
  Class.forName("com.mysql.jdbc.Driver")
  val mysqlDF = spark.read.jdbc(url, table, properties)
  mysqlDF.show()
  mysqlDF.createOrReplaceTempView("Employee")
  val emp_detail = spark.sql("select empname,designation,salary from Employee where salary > 35000")
  emp_detail.write.mode(SaveMode.Overwrite).jdbc(s"${url}", "new_emptable", properties)
  emp_detail.coalesce(1).write.mode(SaveMode.Overwrite).csv("C:/Users/Manimegalai Murugan/Desktop/new_employees.csv")

  
  }
}

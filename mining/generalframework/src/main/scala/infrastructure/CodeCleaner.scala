package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{length,udf}
import scala.io.Source

object  CodeCleaner {

    //val symbolFile = "/opt/mining/data/javaSyntax.txt"
    val symbolFile = "../data/javaSyntax.txt"

    val symbols = Source.fromFile(symbolFile).getLines.mkString.split("[ ,.\"]").filter((str)=> !str.isEmpty)

    def cleanCodeString(str:String):String = {
        str.split("[ ,.\"();]").filter((str)=> !str.isEmpty && symbols.contains(str)).mkString(" ")
    }

    val cleaner = udf((code:String)=> {

        cleanCodeString(code);
    })

    def cleanCode(data:Dataset[Row]):Dataset[Row] = {
        if(data.isEmpty){
            ClassifierLogger.printWarning("code cleaner on an empty dataset")
            return data
        }
        ClassifierLogger.printInfo(" cleaning code ")
        var cleanedata = data.withColumn("cleanedCode",cleaner(data("code")))
        cleanedata.na.drop
    }

    
}

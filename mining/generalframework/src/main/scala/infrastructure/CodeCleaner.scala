package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{length,udf}
import scala.io.Source

object  CodeCleaner {

    val symbolFile = FileResolver.getDataDirectory() + "javaSyntax.txt"

    val symbols = Source.fromFile(symbolFile).getLines.mkString.split("[ ,.\"]").filter((str)=> !str.isEmpty)

    def cleanCodeString(str:String):String = {
        str.split("[ ,.\"();{}]").filter((str)=> !str.isEmpty && symbols.contains(str)).mkString(" ")
    }

    val cleaner = udf((code:String)=> {
        cleanCodeString(code)
    })

    def cleanCode(data:Dataset[Row],colName:String,cleanedColName:String,streaming:Boolean=false):Dataset[Row] = {
        if(streaming){
            return data.withColumn(cleanedColName,data(colName))    //for now, no cleaning on streaming data because kafka streaming arises a lot of problems with udfs
        }
        if(!streaming && data.isEmpty){
            ClassifierLogger.printWarning("code cleaner on an empty dataset")    //PROBLEMS WITH STREAMING DATA
            return data
        }
        ClassifierLogger.printInfo(" cleaning code , invoking udf to filter code ")
        var cleanedata = data.withColumn(cleanedColName,cleaner(data(colName)))
        ClassifierLogger.printInfo(" cleaning code , dropping NAs ")
        cleanedata.na.drop
    }

    
}

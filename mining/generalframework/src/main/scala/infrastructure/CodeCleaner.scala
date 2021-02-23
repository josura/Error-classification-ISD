package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.length

object  CodeCleaner {

    val symbolFile = "data/javaSyntax.txt"

    def cleanCode(data:Dataset[Row]):Dataset[Row] = {
        if(data.isEmpty){
            ClassifierLogger.printWarning("code cleaner on an empty dataset")
            return data
        }
        ClassifierLogger.printInfo(" cleaning code ")



        data.na.drop.filter(length(data("code"))>3)
    }
}

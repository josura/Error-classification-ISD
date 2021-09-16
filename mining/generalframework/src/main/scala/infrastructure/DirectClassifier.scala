package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.types.IntegerType

class DirectClassifier(private var _spark:SparkFacade,private var _dataset:Dataset[Row]) extends Classifier(_spark,_dataset){

    override def initializeModels(){
        if(datasetChanged){
            datasetChanged = false

            val stringIndCode = new StringIndexer().setInputCol("cleanedCode").setOutputCol("label")
            val stringIndSolution = new StringIndexer().setInputCol("cleanedSolution").setOutputCol("label")

            val stringIndCodeModel = stringIndCode fit dataset
            val stringIndSolutionModel = stringIndSolution fit dataset

            val dataSolutionstmp = (stringIndSolutionModel transform dataset).drop("cleanedCode").withColumnRenamed("cleanedSolution","cleanedCode")
            val dataSolutions = dataSolutionstmp.withColumn("label",dataSolutionstmp("label").cast(IntegerType))

            val dataErrorstmp = (stringIndCodeModel transform dataset)
            val dataErrors = dataErrorstmp.withColumn("label",dataErrorstmp("label").cast(IntegerType))

            val neuralLayersSolutions = Array(300,200,dataSolutions.select("label").distinct.count().toInt)
            val neuralLayersErrors = Array(300,200,dataErrors.select("label").distinct.count().toInt)

            modelErrors = createPipelinePerceptrons(neuralLayersSolutions,"cleanedCode") fit dataSolutions

            modelMutations = createPipelinePerceptrons(neuralLayersErrors,"cleanedCode") fit dataErrors
        }
    }
  
}

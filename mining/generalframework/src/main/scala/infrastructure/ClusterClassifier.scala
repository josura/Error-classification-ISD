package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row

class ClusterClassifier(private var _spark:SparkProxy,private var _dataset:Dataset[Row]) extends Classifier(_spark,_dataset){
    override def initializeModels(){
        if(datasetChanged){
            datasetChanged = false
            val expectedElementsForCluster = 3.0
            val numberCluster = (dataset.count/expectedElementsForCluster).ceil.toInt

            val neuralLayers = Array(300,200,numberCluster)

            val ClusteringModelSolution = createPipelineKmeans(numberCluster,"cleanedSolution") fit dataset

            val ClusteringModelCode = createPipelineKmeans(numberCluster,"cleanedCode") fit dataset

            val solutionsClust = (ClusteringModelSolution transform dataset).withColumnRenamed("prediction","label").drop("words","features","cleanedCode").withColumnRenamed("cleanedSolution","cleanedCode")

            val codeClust = (ClusteringModelCode transform dataset).withColumnRenamed("prediction","label").drop("words","features")

            modelErrors = createPipelinePerceptrons(neuralLayers,"cleanedCode") fit solutionsClust

            modelMutations = createPipelinePerceptrons(neuralLayers,"cleanedCode") fit codeClust
        }
    }
  
}

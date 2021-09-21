package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.ml.PipelineModel

class ClusterClassifier(private var _spark:SparkFacade,private var _dataset:Dataset[Row], private var expectedElementsForCluster:Double = 3.0) extends Classifier(_spark,_dataset){
    override def initializeModels(){
        if(scala.reflect.io.File("model/clusterClassifierPipelineErrors").exists && datasetChanged){
            datasetChanged = false
            ClassifierLogger.printInfo("Cluster model loaded from previously trained model")
            modelErrors = PipelineModel.read.load("model/clusterClassifierPipelineErrors")

            modelMutations = PipelineModel.read.load("model/clusterClassifierPipelineMutations")

        } else if(datasetChanged){
            datasetChanged = false
            val numberCluster = (dataset.count/expectedElementsForCluster).ceil.toInt

            val neuralLayers = Array(300,200,numberCluster)

            val ClusteringModelSolution = createPipelineKmeans(numberCluster,"cleanedSolution") fit dataset

            val ClusteringModelCode = createPipelineKmeans(numberCluster,"cleanedCode") fit dataset

            val solutionsClust = (ClusteringModelSolution transform dataset).withColumnRenamed("prediction","label").drop("words","features","cleanedCode").withColumnRenamed("cleanedSolution","cleanedCode")

            val codeClust = (ClusteringModelCode transform dataset).withColumnRenamed("prediction","label").drop("words","features")

            modelErrors = createPipelinePerceptrons(neuralLayers,"cleanedCode") fit solutionsClust

            modelMutations = createPipelinePerceptrons(neuralLayers,"cleanedCode") fit codeClust

            modelMutations.write.overwrite.save("model/clusterClassifierPipelineMutations")
            modelErrors.write.overwrite.save("model/clusterClassifierPipelineErrors")
        }
    }
  
}

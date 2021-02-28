package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}
import org.apache.spark.sql.functions.length
//per convertire colonne in json in dataframe
import org.apache.spark.sql.functions._
//per la creazione di schemi
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.IntegerType

//pipeline e clustering
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vector

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator



class Classifier(var spark:SparkSession,var dataset:Dataset[Row]){
    val bugsFile = FileResolver.getDataDirectory() + "final.json"


    def createPipelineTokenizer() :Pipeline = {
        val tokenizer = new Tokenizer().setInputCol("code").setOutputCol("words")
        val hashingTF = new HashingTF().setNumFeatures(1000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
        new Pipeline().setStages(Array(tokenizer, hashingTF))
    }

    def ClassifyCode(data:Dataset[Row]):Dataset[Row] = {
        data
    }

    def AddErrorSolution(data:Dataset[Row]):Dataset[Row] = {
        data
    }


}
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
import org.apache.spark.ml.feature.{HashingTF, Tokenizer, StringIndexer}
import org.apache.spark.ml.linalg.Vector

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.Word2Vec



abstract class Classifier(var spark:SparkFacade,protected var dataset:Dataset[Row]){
    var datasetChanged:Boolean = true

    protected def createPipelineTokenizerTF(inputCol:String) :Pipeline = {
        val tokenizer = new Tokenizer().setInputCol(inputCol).setOutputCol("words")
        val hashingTF = new HashingTF().setNumFeatures(1000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
        new Pipeline().setStages(Array(tokenizer, hashingTF))
    }

    protected def createPipelineTokenizerW2V(inputCol:String) :Pipeline = {
        val word2Vec = new Word2Vec().setVectorSize(300).setMinCount(0).setInputCol(inputCol).setOutputCol("features")
        new Pipeline().setStages(Array(word2Vec))
    }
    // to create labels
    protected def createPipelineKmeans(numcluster:Int,inputCol:String):Pipeline = {
        val tokenizer = new Tokenizer().setInputCol(inputCol).setOutputCol("words")
        val word2Vec = new Word2Vec().setVectorSize(300).setMinCount(0).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
        val kmeans = new KMeans().setK(numcluster).setSeed(1L)
        new Pipeline().setStages(Array(tokenizer,word2Vec, kmeans))
    }

    protected def createPipelinePerceptrons(layers:Array[Int],inputCol:String) :Pipeline = {
        val tokenizer = new Tokenizer().setInputCol(inputCol).setOutputCol("words")
        val word2Vec = new Word2Vec().setVectorSize(300).setMinCount(0).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
        val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
        new Pipeline().setStages(Array(tokenizer,word2Vec, trainer))
    }

    var modelErrors:PipelineModel=null;
    var modelMutations:PipelineModel=null;

    protected def initializeModels():Unit

    def setDataset(_data:Dataset[Row]){
        if(_data!=null && !_data.isEmpty){
            dataset = _data
            datasetChanged = true
        }
    }


    def ClassifyCode(data:Dataset[Row]):Dataset[Row] = {
        initializeModels()
        var full:Dataset[Row]=null;
        if(data!=null && !data.isEmpty ){
            ClassifierLogger.printInfo("Classifying errors and mutations with "+ this.getClass.getName )
            val mutations = (modelMutations transform data).withColumnRenamed("prediction","labelMutation").withColumnRenamed("probability","probabilityMutation").withColumnRenamed("rawPrediction","rawPredictionMutation")

            val errors = (modelErrors transform data).withColumnRenamed("prediction","labelError").withColumnRenamed("probability","probabilityError").withColumnRenamed("rawPrediction","rawPredictionError").select("ids","code","labelError","probabilityError","rawPredictionError")

            //full = errors.join(mutations,errors("ids")===mutations("ids") && errors("code")===mutations("code"))
            full = errors.join(mutations, Seq("ids","code"))
        }else{
            ClassifierLogger.printWarning("classifying an empty or null dataframe")
        }
        full
    }

    def AddErrorSolution(data:Dataset[Row]):Dataset[Row] = {
        datasetChanged=false
        initializeModels()
        data
        // TODO 
    }


}
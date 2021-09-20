package infrastructure

import org.scalamock.scalatest.MockFactory
//import org.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructType,StringType,IntegerType}

class ClassifierTest extends AnyFunSuite with MockFactory{
    val jsonTest1 = """{"ids":1,"error":"junit.framework.ComparisonFailure","source":"/source/org/jfree/chart/imagemap/StandardToolTipTagFragmentGenerator.java","code":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","solution":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","CodeWithNoComments":"\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","SolutionWithNoComments":"\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","user":"MACHINA","group":"ALL"}"""
    val jsonTest2 = """{"ids":2,"error":"junit.framework.ComparisonFailure","source":"/source/org/jfree/chart/imagemap/StandardToolTipTagFragmentGenerator.java","code":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","solution":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","CodeWithNoComments":"\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","SolutionWithNoComments":"\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","user":"MACHINA","group":"ALL"}"""
    val spark = SparkSession.builder.appName("test").
        //config("spark.sql.crossJoin.enabled", "true").
        master("local[*]").getOrCreate()

    import spark.implicits._

    val bugsFile = FileResolver.getDataDirectory() + "final.json"

    val sparkProxy = new SparkFacade("test classifier")

    val streamDF = Seq(jsonTest1,jsonTest2).toDF("value")
    
    val returnDF = CodeCleaner.cleanCode(Seq((1,"if(test == 0){ funcTest (dest);\n\t vartest = vartest2 + stop;\n}"),(2,"if(test == 0){ funcTest (dest);\n\t vartest = vartest2 + stop;\n}")).toDF("ids","code"),colName = "code",cleanedColName = "cleanedCode") 

    val trainDF = CodeCleaner.cleanCode(CodeCleaner.cleanCode(sparkProxy.sparkReadJson(bugsFile),"CodeWithNoComments","cleanedCode"),"SolutionWithNoComments","cleanedSolution")

    val mockSparkFacade = mock[SparkFacade]

    val clusClassifier:Classifier = new ClusterClassifier(mockSparkFacade,trainDF)

    val direClassifier:Classifier = new DirectClassifier(mockSparkFacade,trainDF)
    
    def testSchema(schema:StructType,names:List[String]):Boolean={
        try{
            names.foreach(stringa=>schema(stringa))
        } catch {
            case e:IllegalArgumentException=>return false
        }
        true
    }

    test("direct classifier returns non empty dataframe(TESTABLE BUT SLOW BECAUSE DATASET IS TOO LITTLE AND CONVERGENCE IS NOT REACHED QUICKLY)"){
        val testDF = direClassifier.ClassifyCode(returnDF)
        assert(!testDF.isEmpty)
        //assert(true)
    }

    test("cluster classifier returns non empty dataframe"){
        val testDF = clusClassifier.ClassifyCode(returnDF)
        assert(!testDF.isEmpty)
    }

    test("cluster classifier returns right number of records"){
        val testDF = clusClassifier.ClassifyCode(returnDF)
        assert(testDF.count() == 2)
    }

    test("cluster classifier returns right schema"){
        val testDF = clusClassifier.ClassifyCode(returnDF)

        val schemaList = List("ids","code","labelError","labelMutation","probabilityError","rawPredictionError","probabilityMutation","rawPredictionMutation")

        assert(testSchema(testDF.schema,schemaList))
    }
}

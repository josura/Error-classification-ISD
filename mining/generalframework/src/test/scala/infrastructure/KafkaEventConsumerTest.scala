package infrastructure

import org.scalamock.scalatest.MockFactory
//import org.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructType,StringType,IntegerType}

class KafkaEventConsumerTest extends AnyFunSuite with MockFactory {
    val jsonTest1 = """{"ids":1,"error":"junit.framework.ComparisonFailure","source":"/source/org/jfree/chart/imagemap/StandardToolTipTagFragmentGenerator.java","code":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","solution":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","CodeWithNoComments":"\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","SolutionWithNoComments":"\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","user":"MACHINA","group":"ALL"}"""
    val jsonTest2 = """{"ids":2,"error":"junit.framework.ComparisonFailure","source":"/source/org/jfree/chart/imagemap/StandardToolTipTagFragmentGenerator.java","code":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","solution":"@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","CodeWithNoComments":"\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","SolutionWithNoComments":"\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n","user":"MACHINA","group":"ALL"}"""
    val jsonTest3 = """[{"ids":1,"error":"junit.framework.AssertionFailedError","source":"/source/org/jfree/chart/util/ShapeList.java","code":"@@ -108,7 +108,14 @@ public boolean equals(Object obj) {\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         return super.equals(obj);\n \n     }\n \n","solution":"@@ -108,7 +108,14 @@ public boolean equals(Object obj) {\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         ShapeList that = (ShapeList) obj;\n         int listSize = size();\n         for (int i = 0; i < listSize; i +) {\n            if (!ShapeUtilities.equal((Shape) get(i), (Shape) that.get(i))) {\n                return false;\n            }\n         }\n         return true;\n \n     }\n \n","CodeWithNoComments":"\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         return super.equals(obj);\n \n     }\n \n","SolutionWithNoComments":"\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         ShapeList that = (ShapeList) obj;\n         int listSize = size();\n         for (int i = 0; i < listSize; i +) {\n            if (!ShapeUtilities.equal((Shape) get(i), (Shape) that.get(i))) {\n                return false;\n            }\n         }\n         return true;\n \n     }\n \n","user":"MACHINA","group":"ALL"},{"ids":2,"error":"junit.framework.AssertionFailedError","source":"/source/org/jfree/chart/util/ShapeList.java","code":"@@ -108,7 +108,14 @@ public boolean equals(Object obj) {\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         return super.equals(obj);\n \n     }\n \n","solution":"@@ -108,7 +108,14 @@ public boolean equals(Object obj) {\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         ShapeList that = (ShapeList) obj;\n         int listSize = size();\n         for (int i = 0; i < listSize; i +) {\n            if (!ShapeUtilities.equal((Shape) get(i), (Shape) that.get(i))) {\n                return false;\n            }\n         }\n         return true;\n \n     }\n \n","CodeWithNoComments":"\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         return super.equals(obj);\n \n     }\n \n","SolutionWithNoComments":"\n         if (!(obj instanceof ShapeList)) {\n             return false;\n         }\n         ShapeList that = (ShapeList) obj;\n         int listSize = size();\n         for (int i = 0; i < listSize; i +) {\n            if (!ShapeUtilities.equal((Shape) get(i), (Shape) that.get(i))) {\n                return false;\n            }\n         }\n         return true;\n \n     }\n \n","user":"MACHINA","group":"ALL"}]"""
    val jsonTest4true = """{"ids":1,user:GIORGIO,group:SELF,code:if(testing=false){String goodbye = "";}}"""
    val schema=new StructType().add("ids",IntegerType).add("error",StringType).add("source",StringType).add("code",StringType).add("solution",StringType).add("CodeWithNoComments",StringType).add("SolutionWithNoComments",StringType).add("user",StringType).add("group",StringType)

    val spark = SparkSession.builder.appName("test").
        master("local[*]").getOrCreate()

    test("kafka Consumer return non empty dataframe") {
        val mockSparkFacade = mock[SparkFacade]
        
        import spark.implicits._

        val returnDF = Seq(jsonTest1).toDF("value")
        (mockSparkFacade.sparkReadStreamKafka _).expects("UserCode").returning(returnDF).once()

        val consumer = new KafkaEventConsumer(mockSparkFacade)

        val data = consumer.Consume("UserCode",schema)

        assert(!data.isEmpty)
        
    }

    def testSchema(schema:StructType,names:List[String]):Boolean={
        try{
            names.foreach(stringa=>schema(stringa))
        } catch {
            case e:IllegalArgumentException=>return false
        }
        true
    }
    test("kafka Consumer return right schema dataframe from json") {
        val mockSparkFacade = mock[SparkFacade]
        import spark.implicits._

        val returnDF = Seq(jsonTest1).toDF("value")
        (mockSparkFacade.sparkReadStreamKafka _).expects("UserCode").returning(returnDF).once()

        val consumer = new KafkaEventConsumer(mockSparkFacade)

        val data = consumer.Consume("UserCode",schema)

        val schemaList = List("ids","source","code","user","group")

        assert(testSchema(data.schema,schemaList))
        
    }
    test("kafka Consumer return right number of records dataframe from json") {
        val mockSparkFacade = mock[SparkFacade]
        import spark.implicits._

        val returnDF = Seq(jsonTest1,jsonTest2).toDF("value")
        (mockSparkFacade.sparkReadStreamKafka _).expects("UserCode").returning(returnDF).once()

        val consumer = new KafkaEventConsumer(mockSparkFacade)

        val data = consumer.Consume("UserCode",schema)


        assert(data.count()==2)
        
    }

    test("kafka Consumer return right dataframe from json") {
        val mockSparkFacade = mock[SparkFacade]
        import spark.implicits._

        val returnDF = Seq(jsonTest1).toDF("value")
        (mockSparkFacade.sparkReadStreamKafka _).expects("UserCode").returning(returnDF).once()

        val consumer = new KafkaEventConsumer(mockSparkFacade)

        val data = consumer.Consume("UserCode",schema).select("ids","error","source","code","solution","user","group")

        val expectedDF = Seq((1,
            "junit.framework.ComparisonFailure",
            "/source/org/jfree/chart/imagemap/StandardToolTipTagFragmentGenerator.java",
            "@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + toolTipText\n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n",
            "@@ -62,7 +62,7 @@ public StandardToolTipTagFragmentGenerator() {\n      * @return The formatted HTML area tag attribute(s).\n      */\n     public String generateToolTipFragment(String toolTipText) {\n        return \" title=\\\"\" + ImageMapUtilities.htmlEscape(toolTipText) \n             + \"\\\" alt=\\\"\\\"\";\n     }\n \n",
            "MACHINA",
            "ALL")).toDF("ids","error","source","code","solution","user","group")

        assert(expectedDF.except(data).isEmpty)
        
    }
}
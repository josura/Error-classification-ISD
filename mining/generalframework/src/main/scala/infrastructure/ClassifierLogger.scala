package infrastructure

import com.typesafe.scalalogging.LazyLogging

import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.io.Source


object ClassifierLogger extends LazyLogging{

  val logFile = FileResolver.getLogsDirectory() + "classifierlog.log"
  //val writer = new FileWriter(new File(logFile))
  val myFormatObj:DateTimeFormatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy HH:mm:ss]");
  
  def printInfo(text:String){
    logger.info(text)
    val writer = new FileWriter(new File(logFile),true)

    //Time
    val myDateObj:LocalDateTime = LocalDateTime.now();


    val formattedDate = myDateObj.format(myFormatObj);

    writer.write(formattedDate + " INFO: "+text+ "\n")
    writer.close()
  }

  def printDebug(text:String){
    logger.debug(text)
    val writer = new FileWriter(new File(logFile),true)
    //Time
    val myDateObj:LocalDateTime = LocalDateTime.now();


    val formattedDate = myDateObj.format(myFormatObj);

    writer.write(formattedDate + " DEBUG: "+text+ "\n")
    writer.close()
  }

  def printWarning(text:String){
    logger.warn(text)
    val writer = new FileWriter(new File(logFile),true)
    
    //Time
    val myDateObj:LocalDateTime = LocalDateTime.now();


    val formattedDate = myDateObj.format(myFormatObj);

    writer.write(formattedDate + " WARNING: "+text+ "\n")
    writer.close()
  }

  def printError(text:String,exception:Throwable){
    logger.error(text,exception)
    val writer = new FileWriter(new File(logFile),true)
    
    //Time
    val myDateObj:LocalDateTime = LocalDateTime.now();


    val formattedDate = myDateObj.format(myFormatObj);

    writer.write(formattedDate + " ERROR: "+text + "\n\t exception: " + exception.toString+ "\n")
    writer.close()
  }
}

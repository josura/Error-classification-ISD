package infrastructure

trait Solution {
  protected var problemtxt:String = ""
  protected var solutiontxt:String = ""

  def getSolution():String = solutiontxt
  def getProblem():String = problemtxt

  def setSolution(_prob:String,_sol:String):Unit

  def getType():String =this.getClass().getSimpleName()
  
}

package infrastructure

class Bug extends Solution{

  override def getSolution(): String = {solutiontxt}

  override def getProblem(): String = {problemtxt}

  override def setSolution(_prob: String, _sol: String): Unit = {
      problemtxt=_prob
      solutiontxt=_sol
  }

  override def toString(): String = {
      problemtxt + "," + solutiontxt + "," +getType()
  }
  
}

package infrastructure

class Mutation extends Solution{

  override def getSolution(): String = {super.getProblem()}

  override def getProblem(): String = {super.getProblem()}

  override def setSolution(_prob: String, _sol: String): Unit = {
      //TODO controls
      solutiontxt=_sol
      problemtxt=_prob
  }

}

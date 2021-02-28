package infrastructure

object FileResolver {
  def getDataDirectory():String = {
        if(sys.env.get("DOCKER_RUNNING").isDefined)
            "/opt/data/"
        else "../data/"
  }
  def getLogsDirectory():String = {
        if(sys.env.get("DOCKER_RUNNING").isDefined)
            "/opt/logs/"
        else "../../logs/"
  }
}

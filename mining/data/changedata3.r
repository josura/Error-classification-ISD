library(jsonlite)

test <- read.csv("test.csv")

notfinal <- read.csv("notfinalBugs.csv")

notfinal["diff2"] <- notfinal$diff


write.csv(notfinal,"almostFinal.csv",row.names = FALSE)


dfcodes <- read.csv("almostFinalcodes.csv")
dfsolutions<- read.csv("almostFinal-solutions.csv")

dfcodes["solution"] <- dfsolutions$solution


write.csv(dfcodes,"Final.csv",row.names = FALSE)

fullfinal <- read.csv("FullFinal.csv")

jsonText <- toJSON(fullfinal)

write(jsonText,file="FullFinalJson.json")

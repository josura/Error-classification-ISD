library(jsonlite)


fullfinal <- read.csv("FullFinal.csv")
fullfinal["user"] <- rep("MACHINA",nrow(fullfinal))
fullfinal["group"] <- rep("ALL",nrow(fullfinal))

jsonText <- toJSON(fullfinal)


write(jsonText,file="final.json")

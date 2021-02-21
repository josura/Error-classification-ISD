install.packages("rjson")

library(rjson)

bugsList <- fromJSON(file="defects4j-bugs.json")

df <- data.frame(matrix(ncol=2,nrow=0))

for (bug in bugsList) {
  df <- rbind(df,c(bug$failingTests[[1]]$error,bug$diff))
}
columnNames <- c("error","diff")
colnames(df) <- columnNames

write.csv(df,"filteredJSON.csv")

jsonText <- toJSON(df)

write(jsonText,file="filteredJson.json")
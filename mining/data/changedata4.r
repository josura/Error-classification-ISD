final <- read.csv("Final.csv")
finalNocomm <- read.csv("FinalNoComments.csv")


final["CodeWithNoComments"] <- finalNocomm$code
final["SolutionWithNoComments"] <- finalNocomm$solution



write.csv(final,"FullFinal.csv",row.names = FALSE)

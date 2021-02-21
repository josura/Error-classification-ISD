install.packages("jsonlite")
install.packages("readr")


library(readr)
library(jsonlite)

stringajson <- read_file("defects4j-bugs.json")

bugs <- fromJSON(stringajson) %>% as.data.frame

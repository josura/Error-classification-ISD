# (Entomomachia) Error-classification-ISD
The aim of this project is to implement a complex distributed architecture with microservices to build an interface that could be used by a user to share some **code** and get predictions of **errors** and **mutations** based on the code of common repositories from [defect4j-dissection](https://github.com/program-repair/defects4j-dissection).

The prediction is done by a neural network (multilayer perceptrons) in the Spark framework, the model is trained with the data from defect4j, the resultant predictions are a vector of feature for the code and 2 labels(one label for errors, 1 label for mutations). 
Code with the same label is deemed similar by the framework and will be returned to end users as possible Errors or Mutations.

The application exposes a web interface(for normal users) and some client API(REST) 

This project was built for a course at university (engineering of distributed systems).

Technologies used in this project:
> - [Elasticsearch](https://www.elastic.co/) as a repository for useful and labelled code and errors.
> - [Spark](https://spark.apache.org/) as the main classification framework and backend behind the prediction
> - [Spring Framework](https://www.google.com/search?client=firefox-b-d&q=spring) as the main component for the web server and the backend for communication between end users and prediction framework
> - [Kafka](https://www.confluent.io/what-is-apache-kafka/?utm_medium=sem&utm_source=google&utm_campaign=ch.sem_br.nonbrand_tp.prs_tgt.kafka_mt.xct_rgn.emea_lng.eng_dv.all_con.kafka-general&utm_term=kafka&creative=&device=c&placement=&gclid=CjwKCAjw7--KBhAMEiwAxfpkWI5WuMmACbZnsIRBemzfwiqFWKfgoY9WfTjr2mPf2p7OdkaOvl1AFhoCdbIQAvD_BwE) as the messaging and event system that makes the whole project event driven.
> - [Docker](https://www.docker.com/) and [Docker-compose](https://docs.docker.com/compose/) to simply create microservices, orchestrate the whole project and make It Scalable.

## IMPLEMENTED
> - Classification framework for errors and mutations based on code passed on input
> - receiving and sending streaming data from kafka, to kafka(to revise after Client and REST API backend is done)
> - Querying elasticsearch and create a remote facade as an interface to the queries(other queries could be added, especially for ignoring users of other organizations).
> - REST APIs
> - Communication framework with kafka, client side, switched with a request-responce pattern to decouple the client request and the whole backbone, the request are now done with transactions registered in redis, when the whole computation of possible errors and mutation has finished, the result are written to redis.
> - Sharing codes and errors via REST API to improve the model
> - Propagation of errors through the transaction status(for the mining backend)

## TODOs
> - Tests for almost everything
> - Access control for users with different roles (access to certain code, access to all code for admins, implement the queries in elasticsearch and this should be done)
> - WEB interface(It was in production but the backbone was more important)
> - Tracing
> - logging analysis by passing logs in ElasticSearch and visualizing them in Kibana (controlling logs file and passing new logs everytime the logfile gets written)
> - Better error handling
> - Better logging
> - Aspects logging

# DEPENDENCIES
> - java openjdk (11 for building and running Spring applications)
> - docker and docker-compose
> - git

# OPTIONAL DEPENDENCIES FOR TESTING
This dependencies are not required to run the application in docker but are useful when developing and testing the services
> - java openjdk 8 for testing scala applications, only java 11 is required to build the Spring image
> - scala and/or sbt
> - postman or curl to test the REST calls

# BUILDING AND RUNNING THE PROJECT
To build and run the whole project
```bash
git clone https://github.com/josura/Error-classification-ISD.git
cd backend/RESTinterface
./mvnw spring-boot:build-image
cd ../..
docker-compose up
```

# USAGE
On a web browser or with http requests, go to http://localhost:8888 and experiment with:
> - GET requests like http://localhost:8888/id where id is a transaction number
> - POST requests like http://localhost:8888 to get a transaction ID used to see if the prediction of errors and mutation has finished, with a request body with the following json structure:
```json
{
    "user": "GIORGIO",
    "group": "SELF",
    "code": "if(testing=false){String goodbye = \"\";}"
}
```
> - POST requests like http://localhost:8888/share to share some code with errors and the corrected code to enhance the model that predicts errors and mutations, with a request body with the following json structure:
```json
{
    "user": "GIORGIO",
    "group": "ALL",
    "code": "if(testing=false){String goodbye = \"\";}",
    "solution": "if(testing==false){String goodbye = \"\";}"
}
```

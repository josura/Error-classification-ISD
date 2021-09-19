ARG SCALA_VER=2.13.1
ARG SBT_VER=1.3.5
FROM hseeberger/scala-sbt:8u222_${SBT_VER}_${SCALA_VER}
ENV SPARK_VERSION=2.4.5
ENV SPARK_HOME /opt/spark


WORKDIR /opt/generalframework

ADD build.sbt build.sbt

RUN sbt compile

#ENTRYPOINT [ "sbt", "test" ]

CMD [ "bash" ]

#ENTRYPOINT [ "sbt", "runMain" , "infrastructure.MainClassification"]
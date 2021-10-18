#!/bin/bash

if [ -n $MINING ]
then
  if [ $MINING = 'classifier' ]
  then
    sbt 'runMain infrastructure.MainClassification'
    exit
  elif [ $MINING = 'improve_model' ]
  then
    sbt 'runMain infrastructure.MainImproveModel'
    exit
  else
    echo "MINING env does not have a defined value"
    exit
  fi
else
  echo -e "MINING not set\n"
fi
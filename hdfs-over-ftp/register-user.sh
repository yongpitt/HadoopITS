#!/bin/bash

if [ "$JAVA_HOME" = "" ]; then
  JAVA_HOME=/usr/java/latest
fi
CLASSPATH=hdfs-over-ftp.jar
for f in lib/*.jar;do
  CLASSPATH=${CLASSPATH}:$f;
done
java -classpath ${CLASSPATH} org.apache.hadoop.contrib.ftp.RegisterUser $1 $2 


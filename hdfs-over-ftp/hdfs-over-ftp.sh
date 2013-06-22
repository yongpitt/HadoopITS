#!/bin/bash

if [ "$JAVA_HOME" = "" ]; then
  JAVA_HOME=/usr/java/latest
fi
JAVA_OPTS="-Xmx1024M"

JAR="hdfs-over-ftp.jar"
JAVA_CMD="$JAVA_HOME/bin/java"

pid=/tmp/hdfs-over-ftp.pid

command="hdfs-over-ftp"
usage="Usage: hdfs-over-ftp.sh (start|stop)"
cmd=$1

case $cmd in

  (start)

    if [ -f $pid ]; then
     if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo $command running as process `cat $pid`.  Stop it first.
        exit 1
     fi
    fi

    echo starting $command
      $JAVA_CMD ${JAVA_OPTS} -jar ${JAR} & echo $! > $pid
    ;;
 
  (stop)

    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo stopping $command
        kill `cat $pid`
		rm $pid
      else
        echo no $command to stop
      fi
    else
      echo no $command to stop
    fi
    ;;

  (*)
    echo $usage
    exit 1
    ;;
esac

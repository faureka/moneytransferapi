#!/bin/bash
CURR_DIR=$(dirname $0)
JVM_SID="revolut-money"
CONF_FILE="$CURR_DIR/config/config.yml"
JAR_FILE="$CURR_DIR/target/moneytransfer.jar"
GC_OPTS="-server -Xms256m -Xmx256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -XX:ConcGCThreads=2 -XX:InitiatingHeapOccupancyPercent=50"
#JMX monitoring
JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=19991 -Djava.rmi.server.hostname=localhost -Dcom.sun.management.jmxremote.rmi.port=18881 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
#heap dump is always useful
JAVA_OPTS="${GC_OPTS} ${JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
#garbage collection debugging
#JAVA_OPTS="${JAVA_OPTS} -verbose:gc -XX:+PrintGCDetails -Xlog:gc:$HOME/logs/memory.log -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps"
#always print stack traces
JAVA_OPTS="${JAVA_OPTS} -XX:-OmitStackTraceInFastThrow"
export JAVA_OPTS

case "$1" in
"start"|"stop"|"kill"|"status"|"restart")
	$CURR_DIR/jctl $1 -s $JVM_SID -c $CONF_FILE -j $JAR_FILE
	;;
*)
	echo "usage: $0 start|stop|kill|restart|status"
	exit 1
	;;
esac

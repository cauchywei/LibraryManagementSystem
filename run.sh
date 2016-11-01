#!/bin/bash

JAR="librarian-1.0-SNAPSHOT.jar"
MAIN_CLASS="xp.librarian.Application"
JAVA_OPTS=""

function get_pids() {
    PIDS=`ps x | grep ${MAIN_CLASS} | grep -v grep | grep java | awk '{print $1}'`
}

function dump() {
    get_pids
    echo "current: ${PIDS}"
}

function stop() {
    echo "stopping.. ${MAIN_CLASS} "

    get_pids
    for PID in ${PIDS};do
        echo "killing ${PID}"
        kill ${PID}
    done

    echo "waiting stop: ${PIDS}"
    while [ $PIDS ];do
        sleep 3
        get_pids
    done
}

function start() {
    echo "starting.. ${MAIN_CLASS}"

    SCRIPT_DIR=$(cd "$(dirname "$0")"; pwd)
    cd ${SCRIPT_DIR}/ &&

    CLASSPATH=${CLASSPATH}:${JAR}
    export CLASSPATH

    mkdir ./logs >/dev/null 2>&1
    nohup java -jar ${JAR} ${MAIN_CLASS} --spring.profiles.active=production 1>app.log 2>app.err &
    sleep 3
    dump
}

stop && start


#!/bin/bash

JAR="librarian-1.0-SNAPSHOT.jar"
MAIN_CLASS="xp.librarian.Application"

function get_pids() {
        PIDS=`ps x | grep ${JAR} | grep -v grep | grep java | awk '{print $1}'`
}

function dump() {
        get_pids
        echo "running: ${PIDS}"
}
function stop() {
        dump

        for PID in ${PIDS};do
                kill ${PID}
                echo "killing: ${PID}"
        done

        while [ $PIDS ];do
                sleep 1
                echo "waiting exited: ${PIDS}"
                get_pids
        done
}

function start() {
        nohup java -jar ${JAR} --spring.profiles.active=production 1>app.log 2>app.err &
        sleep 1
        dump
}

stop
start


#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
scp -P 22 ./target/librarian-1.0-SNAPSHOT.jar xp@xupu.name:~/
ssh xp@xupu.name "~/run.sh"

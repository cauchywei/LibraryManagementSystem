#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
scp -P 22 ./target/librarian-1.0-SNAPSHOT.jar xp@xupu.name:~/

scp -P 22 ./run.sh xp@xupu.name:~/
ssh xp@xupu.name "chmod +x ~/run.sh"
ssh xp@xupu.name "~/run.sh"

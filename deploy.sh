#!/usr/bin/env bash

SCRIPT_DIR=$(cd "$(dirname "$0")"; pwd)
cd ${SCRIPT_DIR}/ &&

mvn clean package -Dmaven.test.skip=true &&
scp -P 22 ./target/librarian-1.0-SNAPSHOT.jar xp@xupu.name:~/librarian-backend/ &&
scp -P 22 ./run.sh xp@xupu.name:~/librarian-backend/ &&
ssh xp@xupu.name "cd ~/librarian-backend/ && chmod +x ./run.sh && ./run.sh"

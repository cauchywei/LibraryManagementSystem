#!/usr/bin/env bash

cnpm run build &&
cd dist/ &&
git add . && git commit -m "update" && git push origin master:dist &&
ssh xp@xupu.name "cd ~/librarian/ && git pull origin dist" &&
cd ..

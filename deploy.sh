#!/usr/bin/env bash
cnpm run build
ssh xp@xupu.name "rm -rf ~/librarian/"
scp -P 22 -r ./dist/ xp@xupu.name:~/librarian/

#! /bin/bash

src="/var/lib/jenkins/workspace/traffic-accident-analysis-calculation-tool/target/gonzo.jar"
dest="/home/milan/projects/traffic-accident-analysis-calculation-tool/target/gonzo.jar"

cp -rf  "$src" "$dest"
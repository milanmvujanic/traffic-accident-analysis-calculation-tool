#! /bin/bash

src="/var/lib/jenkins/workspace/traffic-accident-analysis-calculation-tool/target/traffic-accident-analysis-calculation-tool.jar"
dest="/home/milan/projects/traffic-accident-analysis-calculation-tool/target/traffic-accident-analysis-calculation-tool.jar"

cp -rf  "$src" "$dest"
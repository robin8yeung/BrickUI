#!/bin/bash

# 配置 Java 11 环境
export JAVA_11_HOME=/opt/tools/jdk-11
export JAVA_HOME=$JAVA_11_HOME
alias jdk11="export JAVA_HOME=$JAVA_11_HOME"

java -version
./gradlew --version

# 打包上传
./gradlew -p core clean artifactoryPublish -Dusername=$1 -Dpassword=$2 || { exit 1 ; }
./gradlew -p live clean artifactoryPublish -Dusername=$1 -Dpassword=$2 || { exit 1 ; }
./gradlew -p glide clean artifactoryPublish -Dusername=$1 -Dpassword=$2 || { exit 1 ; }

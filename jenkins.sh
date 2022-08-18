#!/bin/bash

export JAVA_11_HOME=/opt/tools/jdk-11
export JAVA_HOME=$JAVA_11_HOME
alias jdk11="export JAVA_HOME=$JAVA_11_HOME"

java -version
./gradlew --version
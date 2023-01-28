#!/bin/bash

# 打包上传
./gradlew -p core clean assembleRelease || { exit 1 ; }
./gradlew -p live clean assembleRelease || { exit 1 ; }
./gradlew -p glide clean assembleRelease || { exit 1 ; }

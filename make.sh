#!/usr/bin/env bash

mvn -f pom.xml clean package -U

cp target/reinforcement-learning-flappy-bird.jar bin/reinforcement-learning-flappy-bird.jar

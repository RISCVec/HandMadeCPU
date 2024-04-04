#!/bin/bash

sudo apt update
sudo apt upgrade
sudo apt -y install build-essential gcc cmake git default-jdk unzip wget
wget https://downloads.lightbend.com/scala/2.12.19/scala-2.12.19.zip
unzip scala-2.12.19.zip
sudo mv ./scala-2.12.19 /usr/local
wget https://github.com/sbt/sbt/releases/download/v1.9.7/sbt-1.9.7.zip
unzip sbt-1.9.7.zip
sudo mv ./sbt /usr/local
echo "PATH=\$PATH:/usr/local/scala-2.12.19/bin:/usr/local/sbt/bin" >> ~/.bashrc

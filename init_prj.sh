#!/bin/bash

echo ""
echo "********** Init Chisel Project **********"
echo -n " Please Input Project Name: "
read input

mkdir $input $input/src $input/src/main $input/src/main/scala $input/src/test/ $input/src/test/scala $input/src/hex
echo " Create dir."

echo "scalaVersion := \"2.12.13\"

scalacOptions ++= Seq(
  \"-feature\",
  \"-language:reflectiveCalls\",
)

// Chisel 3.5
addCompilerPlugin(\"edu.berkeley.cs\" % \"chisel3-plugin\" % \"3.5.3\" cross CrossVersion.full)
libraryDependencies += \"edu.berkeley.cs\" %% \"chisel3\" % \"3.5.3\"
libraryDependencies += \"edu.berkeley.cs\" %% \"chiseltest\" % \"0.5.3\"" >> $input/build.sbt

echo " Done!"

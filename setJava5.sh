#!/bin/sh

JAVA_HOME=/home/scott/Compilers/java5

export JAVA_HOME 

PATH=$JAVA_HOME/bin:$PATH

export PATH

echo $JAVA_HOME
echo
echo $PATH

mvn assembly:assembly

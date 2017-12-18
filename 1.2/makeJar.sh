#!/bin/bash
echo "Main-Class: Main" > manifest.txt
javac ../1.1/*.java
mv ../1.1/*.class .
jar cmf manifest.txt  ThreadTester.jar *.class
rm manifest.txt
rm *.class
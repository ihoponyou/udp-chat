@echo off
del /q/s "build"
mkdir build
javac -sourcepath "src" -d "build" "src/org/chat/*.java"

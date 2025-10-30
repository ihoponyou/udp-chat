@echo off
if exist .\build\ (@rd /s /q .\build) else (@mkdir build)
javac -sourcepath src -d build src/org/chat/*.java

mkdir -p build
rm -rf build/*
javac -sourcepath src -d build src/org/chat/*.java

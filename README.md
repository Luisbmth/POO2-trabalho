
antes de compilar e executar:
cd release/v4.0/src

/////////////////////////////////////////////

compilar o codigo:
javac main.java


/////////////////////////////////////////////

executar o codigo :
java main.java


////////////////////////////////////////////

comando usado pro docs:
javadoc -d docs src/*.java


/////////////////////////////////////////////


comando usado para tests:

volte para a raiz do projeto e execute:

javac -d out/tests -cp "lib\junit-platform-console-standalone-1.10.0.jar" release\v4.0\src\*.java
java -jar lib\junit-platform-console-standalone-1.10.0.jar --class-path out/tests --scan-class-path
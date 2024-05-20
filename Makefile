all: 
	kotlinc dot.kt -include-runtime -d dot.jar
	java -jar dot.jar matrixA.txt matrixB.txt

clean:
	rm -f dot.jar
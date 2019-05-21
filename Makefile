all: ga

ga:
	@javac -encoding UTF8 $$(find -name "*.java")
	@cd src/main/java; jar -cvfm ${CURDIR}/ga.jar MANIFEST.MF $$(find -name "*.class")

run:
	@timeout 3m java -Dfile.encoding=utf-8 -jar ga.jar maxcut.in maxcut.out

clean:
	@rm -rf $$(find -name "*.class")
	@rm -rf
	@rm ga.jar
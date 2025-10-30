# Compiler
JAVAC = javac
JAVA = java

# source files
SOURCES = FSA.java State.java Transition.java NFA.java DFA.java Main.java

# default target: compile all files
all: compile

# compile all Java files
compile:
	$(JAVAC) $(SOURCES)

# run the program
run: compile
	$(JAVA) Main

# clean compiled files
clean:
	rm -f *.class

# phony targets
.PHONY: all compile run clean

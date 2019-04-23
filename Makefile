# Set the file name of your jar package:
JAR_PKG = splender.jar

# Set your entry point of your java app:
ENTRY_POINT = model.Game

SOURCE_FILES = \
	src/model/Game.java \
	src/model/Anime.java \
	src/model/Card.java \
	src/model/Coin.java \
	src/model/Common.java \
	src/model/Diamond.java \
	src/model/LogIn.java \
	src/model/Player.java

JAVAC = javac
JFLAGS = -encoding UTF-8


vpath %.class bin
vpath %.java src
 
# show help message by default
Default:
	make build
	make run

build:
	$(JAVAC) -cp bin -d bin $(JFLAGS) $(SOURCE_FILES)

rebuild: clean build
	 
clean:
	rm -frv bin/*

run:
	java -cp bin $(ENTRY_POINT)
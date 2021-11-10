SHELL := /bin/bash
REACT_HOST := localhost
REACT_BROWSER=none
REACT_APP_HOST_WS := $(REACT_HOST)

# NOTE to debug frontend we must use REACT_HOST := localhost and forword port 8460 manually on vscode to skip cert problems

run:
	@./mvnw spring-boot:run

# use -e to see full error log
build:
	@./mvnw clean package

# https://stackoverflow.com/questions/5891123/specifying-jvm-arguments-when-calling-a-jar-file
# https://www.fatalerrors.org/a/springboot-solves-apr-based-apache-tomcat-native-library-tips-under-linux.html
runBuild:
# -Xint -Djava.compiler=NONE 
	@java -Xms256m -Xmx512m -Djava.awt.headless=true -jar -Djava.library.path=/usr/local/lib -jar target/citizencardreaderapi-0.0.1-SNAPSHOT.jar

startServer:
	@HTTP_SERVER_URI=0.0.0.0:$(REACT_APP_PORT_WS) \
		cargo run -- start-server

updateService:
	@sudo cp ccserver.service /etc/systemd/system
		

.PHONY: build

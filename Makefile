SHELL := /bin/bash
REACT_HOST := localhost
REACT_BROWSER=none
REACT_APP_HOST_WS := $(REACT_HOST)

# NOTE to debug frontend we must use REACT_HOST := localhost and forword port 8460 manually on vscode to skip cert problems

run:
	@./mvnw spring-boot:run

# use -e to see full error log
build:
	@./mvnw package

buildClean:
	@./mvnw clean package

cleanInstall:
	@./mvnw clean install

# https://stackoverflow.com/questions/5891123/specifying-jvm-arguments-when-calling-a-jar-file
# https://www.fatalerrors.org/a/springboot-solves-apr-based-apache-tomcat-native-library-tips-under-linux.html
runBuild:
# -Xint -Djava.compiler=NONE
# above works to, same as -Djava.library.path=/usr/local/lib
# LD_LIBRARY_PATH=/usr/local/lib
	@java -Xms512m -Xmx1024m -Djava.awt.headless=true -Djava.library.path=/usr/local/lib -jar target/citizencardreaderapi-0.0.1-SNAPSHOT.jar

runClass:
# TODO: the trick to use in system service without the annoying error
#	extracted from vscode launch debugger, and remove some stuff like debug etc
	@/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Djava.library.path=/usr/local/lib -cp /home/mario/Development/SpringBootCitizenCardReaderRestService/target/classes:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.5.5/spring-boot-starter-web-2.5.5.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter/2.5.5/spring-boot-starter-2.5.5.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot/2.5.5/spring-boot-2.5.5.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/2.5.5/spring-boot-autoconfigure-2.5.5.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter-logging/2.5.5/spring-boot-starter-logging-2.5.5.jar:/home/mario/.m2/repository/ch/qos/logback/logback-classic/1.2.6/logback-classic-1.2.6.jar:/home/mario/.m2/repository/ch/qos/logback/logback-core/1.2.6/logback-core-1.2.6.jar:/home/mario/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.14.1/log4j-to-slf4j-2.14.1.jar:/home/mario/.m2/repository/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar:/home/mario/.m2/repository/org/slf4j/jul-to-slf4j/1.7.32/jul-to-slf4j-1.7.32.jar:/home/mario/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/home/mario/.m2/repository/org/yaml/snakeyaml/1.28/snakeyaml-1.28.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter-json/2.5.5/spring-boot-starter-json-2.5.5.jar:/home/mario/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.5/jackson-databind-2.12.5.jar:/home/mario/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.5/jackson-annotations-2.12.5.jar:/home/mario/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.5/jackson-core-2.12.5.jar:/home/mario/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.5/jackson-datatype-jdk8-2.12.5.jar:/home/mario/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.5/jackson-datatype-jsr310-2.12.5.jar:/home/mario/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.5/jackson-module-parameter-names-2.12.5.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/2.5.5/spring-boot-starter-tomcat-2.5.5.jar:/home/mario/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.53/tomcat-embed-core-9.0.53.jar:/home/mario/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/9.0.53/tomcat-embed-el-9.0.53.jar:/home/mario/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.53/tomcat-embed-websocket-9.0.53.jar:/home/mario/.m2/repository/org/springframework/spring-web/5.3.10/spring-web-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-beans/5.3.10/spring-beans-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-webmvc/5.3.10/spring-webmvc-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-aop/5.3.10/spring-aop-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-context/5.3.10/spring-context-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-expression/5.3.10/spring-expression-5.3.10.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter-websocket/2.5.5/spring-boot-starter-websocket-2.5.5.jar:/home/mario/.m2/repository/org/springframework/spring-messaging/5.3.10/spring-messaging-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-websocket/5.3.10/spring-websocket-5.3.10.jar:/home/mario/.m2/repository/org/springframework/boot/spring-boot-starter-data-neo4j/2.5.5/spring-boot-starter-data-neo4j-2.5.5.jar:/home/mario/.m2/repository/org/springframework/data/spring-data-neo4j/6.1.5/spring-data-neo4j-6.1.5.jar:/home/mario/.m2/repository/org/apiguardian/apiguardian-api/1.1.1/apiguardian-api-1.1.1.jar:/home/mario/.m2/repository/org/neo4j/neo4j-cypher-dsl/2021.2.3/neo4j-cypher-dsl-2021.2.3.jar:/home/mario/.m2/repository/org/neo4j/driver/neo4j-java-driver/4.2.7/neo4j-java-driver-4.2.7.jar:/home/mario/.m2/repository/org/reactivestreams/reactive-streams/1.0.3/reactive-streams-1.0.3.jar:/home/mario/.m2/repository/org/springframework/spring-tx/5.3.10/spring-tx-5.3.10.jar:/home/mario/.m2/repository/org/springframework/data/spring-data-commons/2.5.5/spring-data-commons-2.5.5.jar:/home/mario/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/mario/Development/SpringBootCitizenCardReaderRestService/libs/x86_64/pteidlibj.jar:/home/mario/.m2/repository/org/projectlombok/lombok/1.18.20/lombok-1.18.20.jar:/home/mario/.m2/repository/org/springframework/spring-core/5.3.10/spring-core-5.3.10.jar:/home/mario/.m2/repository/org/springframework/spring-jcl/5.3.10/spring-jcl-5.3.10.jar com.solidarychain.citizencardreaderapi.Application

updateService:
	@sudo cp ccserver.service /etc/systemd/system && \
		sudo systemctl daemon-reload

serverStart:
	@sudo service ccserver start

serverStop:
	@sudo service ccserver stop

serverStatus:
	@sudo service ccserver status

serverJournal:
	@sudo journalctl -f -uccserver

# startServer:
# 	@HTTP_SERVER_URI=0.0.0.0:$(REACT_APP_PORT_WS) \
# 		cargo run -- start-server

.PHONY: build

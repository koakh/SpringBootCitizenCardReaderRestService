# NOTES

- [NOTES](#notes)
	- [TLDR](#tldr)
	- [Links](#links)
		- [Autenticação.gov](#autenticaçãogov)
		- [Tooling](#tooling)
		- [SpringBoot](#springboot)
			- [Spring Guide: React.js and Spring Data REST](#spring-guide-reactjs-and-spring-data-rest)
			- [CORS: Fix Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource](#cors-fix-cross-origin-request-blocked-the-same-origin-policy-disallows-reading-the-remote-resource)
			- [Base URL](#base-url)
		- [Java](#java)
			- [HttpsURLConnection : Read the Response From Input Stream](#httpsurlconnection--read-the-response-from-input-stream)
			- [HttpsURLConnection : Read the Response From Input Stream](#httpsurlconnection--read-the-response-from-input-stream-1)
			- [Certificates and Thrust Store](#certificates-and-thrust-store)
			- [Jackson ObjectMapper](#jackson-objectmapper)
	- [Create Project with Spring initialzr](#create-project-with-spring-initialzr)
	- [Use pteidlibj in project](#use-pteidlibj-in-project)
	- [React Frontend with CRA and Typescript](#react-frontend-with-cra-and-typescript)
		- [1. create a cra app ex in .cra](#1-create-a-cra-app-ex-in-cra)
		- [2. create folder `frontend` and copy cra files into it](#2-create-folder-frontend-and-copy-cra-files-into-it)
		- [3. edit `pom.xml` and add **building and packaging** maven plugins](#3-edit-pomxml-and-add-building-and-packaging-maven-plugins)
		- [4 add `src/main/java/com/solidarychain/citizencardreaderapi/config/WebMvcConfig.java`, this will do the magic trick to prefix all `@RestController` annotated controllers with `/api`, and leaving `/` free for serve frontend](#4-add-srcmainjavacomsolidarychaincitizencardreaderapiconfigwebmvcconfigjava-this-will-do-the-magic-trick-to-prefix-all-restcontroller-annotated-controllers-with-api-and-leaving--free-for-serve-frontend)
	- [React WebSockets/Stomp](#react-websocketsstomp)
	- [GrapqhQL Api Notes](#grapqhql-api-notes)
		- [Some Requests](#some-requests)

## TLDR

```shell
$ git clone https://github.com/koakh/SpringBootCitizenCardReaderRestService.git
$ ./mvnw install
$ ./mvnw spring-boot:run
$ curl localhost:8080/api/card/read
```

## Links

### Autenticação.gov

- [pt.gov.cartaodecidadao:pteidlib v3.5.0](https://amagovpt.github.io/docs.autenticacao.gov/sdk/java/overview-summary.html)

- [X] Get events for insertion and removal of cards
- [X] Get identification information
- [-] Get citizen picture file
- [-] Get address information
- [-] How to read or write personal notes
- [-] Signatures with eID card (citizen signature certificate)
- [-] How to sign PDF documents (PAdES)
- [-] How to sign any type of file (XAdES signature format)
- [-] Timestamp Server configuration
- [-] Verify a PAdES signature
- [-] Verify a XAdES signature

### Tooling

- [Java in Visual Studio Code](https://code.visualstudio.com/docs/languages/java)
- [How to Use Visual Studio Code with Java?](https://www.baeldung.com/java-visual-studio-code)

### SpringBoot

#### Spring Guide: React.js and Spring Data REST

- [React.js and Spring Data REST](https://spring.io/guides/tutorials/react-and-spring-data-rest/)
- [Repo](https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-react)

- `located@koakh-laptop:/home/mario/Development/SpringBoot/tut-react-and-spring-data-rest`

#### CORS: Fix Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource

fix ws : `cors Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource at http://localhost:8080/handler/info?t=1635372697890. (Reason: CORS request did not succeed).`

- [25.2.1 Create and Configure a WebSocketHandler](https://docs.spring.io/spring-framework/docs/4.2.4.RELEASE/spring-framework-reference/html/websocket.html#websocket-server-handler)
- [25.2.6 Configuring allowed origins](https://docs.spring.io/spring-framework/docs/4.2.4.RELEASE/spring-framework-reference/html/websocket.html#websocket-server-allowed-origins)

#### Base URL

How to set base url for rest in spring boot?

- [How to set base url for rest in spring boot?: answear](https://stackoverflow.com/a/58256259)

> this will leave `/` for react and thymeleaft if we need it and `/api` for all annotated `RestControllers`

check `src/main/java/com/solidarychain/citizencardreaderapi/config/WebMvcConfig.java`

### Java

#### HttpsURLConnection : Read the Response From Input Stream

- [Read the Response From Input Stream](https://www.baeldung.com/httpurlconnection-post#8-read-the-response-from-input-stream)

#### HttpsURLConnection : Read the Response From Input Stream

- [Como corrigir o erro "Java.security.cert.CertificateException: No subject alternative names present"?](https://www.ti-enxame.com/pt/java/como-corrigir-o-erro-java.security.cert.certificateexception-no-subject-alternative-names-present/1043257709/)


```java
public static void disableHttpsVerification(String host) {
  javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
    public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
      return hostname.equals(host);
    }
  });
}
```

create a helper function, and use it before request's

```java
public static void graphql(String fqdn, String serverUi) {
  // disable https verification on host
  disableHttpsVerification(fqdn);
```

#### Certificates and Thrust Store

this problem occurs when call `HttpUtils.graphql` to request unprotected graphql api (without valid certificate)

- [Java: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target](https://stackoverflow.com/questions/6908948/java-sun-security-provider-certpath-suncertpathbuilderexception-unable-to-find)
- [answear](https://stackoverflow.com/a/59596185)

There is a lot of way to solve this..., One way is set the TrustStore certificates in a keystore file and put it in the path of the application, and set these system properties in the main method:

```shell
# first start the grapqhql api
$ cd ~/Development/Neo4j/TypescriptNodeNeo4jGraphqlLibraryStarter
$ npm run neo4j-gqllib-starter:server
```

**download certificate with firefox** and copy to project path

with chromium

![image](attachments/2021-10-30_19-09.png)

![image](attachments/2021-10-30_19-10.png)

![image](attachments/2021-10-30_19-11.png)

with firefox

![image](attachments/2021-10-30_19-15.png)

![image](attachments/2021-10-30_19-16.png)

![image](attachments/2021-10-30_19-17.png)

with firefox settings, open firefox in `about:preferences#privacy`, note certificate only appear if we accept it

![image](attachments/2021-10-30_19-23.png)

![image](attachments/2021-10-30_19-19.png)

```shell
$ cat localhost-chain.pem
-----BEGIN CERTIFICATE-----
MIIDCzCCAfOgAwIBAgIUeyleEL2B0TAS1ruccI9d85JU3nswDQYJKoZIhvcNAQEL
BQAwFDESMBAGA1UEAwwJbG9jYWxob3N0MCAXDTIxMTAxMjIwNTkzOFoYDzIxMjEw
OTE4MjA1OTM4WjAUMRIwEAYDVQQDDAlsb2NhbGhvc3QwggEiMA0GCSqGSIb3DQEB
AQUAA4IBDwAwggEKAoIBAQDZkVRpatY89OhNlmnNox3mPI2EX142rURmWG5i0EI/
UI2+fZtJAD20rpZAMTLyDhQxD5DnB8Ca6Mq9ZPSebYMUmf+zXvS87PwyE0iFj8Rz
7QPr2Mck7GWe3EXCaDL2FofKk0nJnX7U2gF5ALRd5ozKDyGb/PDu/yPftr6T5+mW
qlBzuyTwcN5rg78IbP4pXtD53OYdh5erg4vtOht61ptQ4eWpBs+Nt/otvijCsDXm
S8thn8IHVeVsIr+60y7wQMImy7I2xYu1DIfTPqvlhDVMyQ8yH7nvYkMnMVdHFGWB
ctz0fbQnMenYyvtqrDtTxVF+Gt7LHhG8QcGabF+4049XAgMBAAGjUzBRMB0GA1Ud
DgQWBBR2S2xH3RSKwF/WIiBtTzFEh8WuQDAfBgNVHSMEGDAWgBR2S2xH3RSKwF/W
IiBtTzFEh8WuQDAPBgNVHRMBAf8EBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQBg
uLCgQDgzA56QtbtpuRLvGV50Xh4jATqXDckusrD8SlYF2+pCY325EpyCzBqJeEWQ
8dpiNQuGGFfFFy6Mv+Um0mqLvdvxfOZfwTgdBSWwEKORh9KCrZ6l0yNhUlVZuqjR
f3OQ5gE+w27SO6OPS9YivKHc1ShnOoejkWZCHXd3yQGt+hc1zQoWpUAuc7GUNuB6
K929fMetXpi3qGBOw92BkJeQvl7WxpVoTLq/lLMuyoVzurqK0OTN4+H1iwPIaS8P
AWjGx38Iz+OMT2auN46wX+t/lqkDxUTLXTuStflZZX1fQpaJV95O7f4rDQFyw9YJ
oPQNU34zpvIMFKTyGyGq
-----END CERTIFICATE-----
```

now create `trust-store.jks`

```shell
$ scp /home/mario/Downloads/localhost* 192.168.122.160:/home/mario/Development/SpringBootCitizenCardReaderRestService
$ keytool -import -alias ca -keystore trust-store.jks -storepass TrustStore -trustcacerts -file localhost-chain.pem
Owner: CN=localhost
Issuer: CN=localhost
Serial number: 7b295e10bd81d13012d6bb9c708f5df39254de7b
Valid from: Tue Oct 12 20:59:38 UTC 2021 until: Thu Sep 18 20:59:38 UTC 2121
```

note from post `I created the keystore file **from a Certificate authority CA .crt** file in this way:`

```shell
$ keytool -import -alias ca -keystore trust-store.jks -storepass TrustStore -trustcacerts -file ca.crt
```

- [Java: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target](https://stackoverflow.com/questions/6908948/java-sun-security-provider-certpath-suncertpathbuilderexception-unable-to-find)

now add to `src/main/java/com/solidarychain/citizencardreaderapi/Application.java`

```java
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
    System.setProperty("javax.net.ssl.trustStore", "trust-store.jks");
    System.setProperty("javax.net.ssl.trustStorePassword", "TrustStore");
	}
}
```

done now we can connect to grapqhql api without a valid certificate

#### Jackson ObjectMapper

- [Intro to the Jackson ObjectMapper](https://www.baeldung.com/jackson-object-mapper-tutorial)







## Create Project with Spring initialzr

- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)

```shell
$ ./mvnw spring-boot:run

$ curl localhost:8080
Greetings from Spring Boot!
```

## Use pteidlibj in project

- [Add external library .jar to Spring boot .jar internal /lib](https://stackoverflow.com/questions/30207842/add-external-library-jar-to-spring-boot-jar-internal-lib)

add to Application and let it fails

```java
import pt.gov.cartaodecidadao.*;
  ...
  System.loadLibrary("pteidlibj");
```

Native code library failed to load.
java.lang.UnsatisfiedLinkError: no pteidlibj in java.library.path: [/usr/java/packages/lib, /usr/lib/aarch64-linux-gnu/jni, /lib/aarch64-linux-gnu, /usr/lib/aarch64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]

cp libs/arm64/pteidlibj.jar /lib/aarch64-linux-gnu/

[WARNING] Some problems were encountered while building the effective model for com.solidarychain:citizencardreaderapi:jar:0.0.1-SNAPSHOT
[WARNING] 'dependencies.dependency.systemPath' for pt.gov.cartaodecidadao:pteidlibj:jar should not point at files within the project directory, ${basedir}/libs/arm64/pteidlibj.jar will be unresolvable by dependent projects @ line 46, column 16
[WARNING] 'build.plugins.plugin.(groupId:artifactId)' must be unique but found duplicate declaration of plugin org.springframework.boot:spring-boot-maven-plugin @ line 80, column 12


- [ ] try delete /home/mario/Development/JavaCitizenCardReader/app/lib/arm64/pteidlibj.jar
- [ ] use only files from /usr/local/lib (new fix)



		<!-- require to use pteidlibj -->
		<dependency>
			<groupId>pt.gov.cartaodecidadao</groupId>
			<artifactId>pteidlibj</artifactId>
			<version>1.0.0</version>
			<scope>system</scope>
			<systemPath>${basedir}/libs/arm64/pteidlibj.jar</systemPath>
		</dependency>


<environmentVariables>
	<LD_LIBRARY_PATH>/usr/local/lib</LD_LIBRARY_PATH>
</environmentVariables>
<systemPropertyVariables>
	<java.library.path>/usr/local/lib</java.library.path>
</systemPropertyVariables>

and MUST
trick is  ./mvnw clean
./mvnw spring-boot:run

now we have lib working



now gives other error






debug keeps show error
Native code library failed to load. 
java.lang.UnsatisfiedLinkError: no pteidlibj in java.library.path: [/usr/java/packages/lib, /usr/lib/aarch64-linux-gnu/jni, /lib/aarch64-linux-gnu, /usr/lib/aarch64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]

add "vmArgs": "-Djava.library.path=/usr/local/lib" to launch.json

with this we can see MAVEN > dependencies > pt

vscjava.vscode-java-dependency








## React Frontend with CRA and Typescript

- [CRUD Application With React and Spring Boot](https://www.baeldung.com/spring-boot-react-crud)

### 1. create a cra app ex in .cra

### 2. create folder `frontend` and copy cra files into it

### 3. edit `pom.xml` and add **building and packaging** maven plugins

```shell
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/">	<properties>
		<!-- react frontend -->
		<frontend-maven-plugin.version>1.6</frontend-maven-plugin.version>
		<maven-resources-plugin.version>3.1.0</maven-resources-plugin.version>
		<node.version>v14.18.0</node.version>
		<yarn.version>v1.12.1</yarn.version>
    ...
	</dependencies>
	<build>
		<plugins>
			<!-- react frontend -->
			<plugin>
				<artifactId>maven-resources-plugin</artifactId>
				<version>${maven-resources-plugin.version}</version>
				<executions>
					<execution>
						<id>copy-resources</id>
						<phase>process-classes</phase>
						<goals>
							<goal>copy-resources</goal>
						</goals>
						<configuration>
							<!-- <outputDirectory>${basedir}/target/classes/static</outputDirectory> -->
							<outputDirectory>${basedir}/src/main/resources/META-INF/resources</outputDirectory>
							<resources>
								<resource>
									<directory>${project.basedir}/frontend/build</directory>
								</resource>
							</resources>
						</configuration>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>com.github.eirslett</groupId>
				<artifactId>frontend-maven-plugin</artifactId>
				<version>${frontend-maven-plugin.version}</version>
				<configuration>
					<workingDirectory>frontend</workingDirectory>
				</configuration>
				<executions>
					<execution>
						<id>install node</id>
						<goals>
							<goal>install-node-and-yarn</goal>
						</goals>
						<configuration>
							<nodeVersion>${node.version}</nodeVersion>
							<yarnVersion>${yarn.version}</yarnVersion>
						</configuration>
					</execution>
					<execution>
						<id>yarn install</id>
						<goals>
							<goal>yarn</goal>
						</goals>
						<phase>generate-resources</phase>
					</execution>
					<execution>
						<id>yarn test</id>
						<goals>
							<goal>yarn</goal>
						</goals>
						<phase>test</phase>
						<configuration>
							<arguments>test</arguments>
							<environmentVariables>
								<CI>true</CI>
							</environmentVariables>
						</configuration>
					</execution>
					<execution>
						<id>yarn build</id>
						<goals>
							<goal>yarn</goal>
						</goals>
						<phase>compile</phase>
						<configuration>
							<arguments>build</arguments>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

> note for `<outputDirectory>${basedir}/src/main/resources/META-INF/resources</outputDirectory>` this will build react frontend in `resources/META-INF/resources` path, that will be server as `http://localhost:8080`

### 4 add `src/main/java/com/solidarychain/citizencardreaderapi/config/WebMvcConfig.java`, this will do the magic trick to prefix all `@RestController` annotated controllers with `/api`, and leaving `/` free for serve frontend

```java
@Override
public void configurePathMatch(PathMatchConfigurer configurer) {
  configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
}
```

## React WebSockets/Stomp

- [react-stomp](https://www.npmjs.com/package/react-stomp)

> A working implementation using Spring Boot and react-talk can be found at [spring-websocket-template](https://github.com/lahsivjar/spring-websocket-template/tree/master/with-sockjs)

- [react-stomp-hooks](https://www.npmjs.com/package/react-stomp-hooks)


```shell
$ cd frontend
$ npm install --save react-stomp-hooks
```

## GrapqhQL Api Notes

### Some Requests

```shell
$ HOST=127.0.0.1:5001
$ HOST=192.168.31.206:5001
$ HOST=192.168.122.1:5001
$ curl -k --request POST \
  --url https://${HOST}/graphql \
  --header 'content-type: application/json' \
  --header 'user-agent: vscode-restclient' \
  --data '{"query":"mutation SignInMutation
  ($signUpEmail: String!, $signUpPassword: String!) { signIn(email: $signUpEmail, password: $signUpPassword)}","variables":{"signUpEmail":"admin@admin.com","signUpPassword":"password" } }' \
	| jq

$ curl -k --request POST \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlNTBlYmE5YS1mZmZhLTQ1MTYtYWYwMS05ZmRjZWExNjRmZmMiLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImlhdCI6MTYzNDQ5NDQ0OH0.GlM6PXzRHXd16p_r5dG8GV6MAVEVdlxbgqYJxeNecUw' \
    --header 'content-type: application/json' \
    --url https://localhost:5001/graphql \
    --data '{"query":"mutation CreateUsersMutation($input: [UserCreateInput!]!) {\n  createUsers(input: $input) {\n    users {\n      id\n      email\n      roles\n    }\n  }\n}","variables":"{\n  \"input\": {\n    \"email\": \"john@mail.com\",\n    \"roles\": \"ROLE_USER\"\n  }\n}"}'
```





curl -k --request POST \

  --url https://192.168.31.206:5001/graphql \
  --header 'content-type: application/json' \
  --header 'user-agent: vscode-restclient' \
  --data '{"query":"mutation SignInMutation
  ($signUpEmail: String!, $signUpPassword: String!) {\n  signIn(email: $signUpEmail, password: $signUpPassword)\n}","variables":{"signUpEmail":"admin@admin.com","signUpPassword":"password"}}' | jq

request create user

HOST=192.168.31.206:5001
JWT=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlNTBlYmE5YS1mZmZhLTQ1MTYtYWYwMS05ZmRjZWExNjRmZmMiLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImlhdCI6MTYzNDQ5NDQ0OH0.GlM6PXzRHXd16p_r5dG8GV6MAVEVdlxbgqYJxeNecUw
EMAIL=jane@mail.com
curl -k --request POST \
	--header "Authorization: Bearer ${JWT}" \
	--header "content-type: application/json" \
	--url https://${HOST}/graphql \
	--data '{ "query" : "mutation CreateUsersMutation($input: [UserCreateInput!]!) { createUsers(input: $input) { users { id email roles } } }","variables":"{ \"input\": { \"email\": \"'${EMAIL}'\", \"roles\": \"ROLE_USER\" } }"}' \
	| jq

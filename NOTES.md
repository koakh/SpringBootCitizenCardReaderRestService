# NOTES

## Links

### Autenticação.gov

MAIN IMPORTANT LINK
- https://amagovpt.github.io/docs.autenticacao.gov/sdk/java/overview-summary.html

### SpringBoot



- https://www.baeldung.com/httpurlconnection-post#8-read-the-response-from-input-stream
- https://stackoverflow.com/questions/6908948/java-sun-security-provider-certpath-suncertpathbuilderexception-unable-to-find

There is a lot of way to solve this...

One way is set the TrustStore certificates in a keystore file and put it in the path of the application, and set these system properties in the main method:

public static void main(String[] args) {
  System.setProperty("javax.net.ssl.trustStore", "trust-store.jks");
  System.setProperty("javax.net.ssl.trustStorePassword", "TrustStore");
}

I created the keystore file from a Certificate authority CA .crt file in this way:

keytool -import -alias ca -keystore trust-store.jks -storepass TrustStore -trustcacerts -file ca.crt

```shell
$ keytool -import -alias ca -keystore trust-store.jks -storepass TrustStore -trustcacerts -file /tmp/localhost.pem
```

- https://www.ti-enxame.com/pt/java/como-corrigir-o-erro-java.security.cert.certificateexception-no-subject-alternative-names-present/1043257709/



https://code.visualstudio.com/docs/languages/java


https://www.baeldung.com/jackson-object-mapper-tutorial




HOST=127.0.0.1:5001
HOST=192.168.31.206:5001
HOST=192.168.122.1:5001
curl -k --request POST \
  --url https://${HOST}/graphql \
  --header 'content-type: application/json' \
  --header 'user-agent: vscode-restclient' \
  --data '{"query":"mutation SignInMutation
  ($signUpEmail: String!, $signUpPassword: String!) { signIn(email: $signUpEmail, password: $signUpPassword)}","variables":{"signUpEmail":"admin@admin.com","signUpPassword":"password" } }' \
	| jq


curl -k --request POST \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlNTBlYmE5YS1mZmZhLTQ1MTYtYWYwMS05ZmRjZWExNjRmZmMiLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImlhdCI6MTYzNDQ5NDQ0OH0.GlM6PXzRHXd16p_r5dG8GV6MAVEVdlxbgqYJxeNecUw' \
    --header 'content-type: application/json' \
    --url https://localhost:5001/graphql \
    --data '{"query":"mutation CreateUsersMutation($input: [UserCreateInput!]!) {\n  createUsers(input: $input) {\n    users {\n      id\n      email\n      roles\n    }\n  }\n}","variables":"{\n  \"input\": {\n    \"email\": \"john@mail.com\",\n    \"roles\": \"ROLE_USER\"\n  }\n}"}'


## Create Project with Spring initialzr

- https://spring.io/guides/gs/spring-boot/

```shell
$ ./mvnw spring-boot:run

$ curl localhost:8080
Greetings from Spring Boot!
```

## Use pteidlibj in project

- [Add external library .jar to Spring boot .jar internal /lib](https://stackoverflow.com/questions/30207842/add-external-library-jar-to-spring-boot-jar-internal-lib)

add to Application and let it fails

```
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

add "vmArgs": "-Djava.library.path=/usr/local/lib"
to launch.json

with this we can see MAVEN > dependencies > pt

vscjava.vscode-java-dependency


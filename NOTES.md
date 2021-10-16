# NOTES

curl -k --request POST \

  --url https://192.168.31.206:5001/graphql \
  --header 'content-type: application/json' \
  --header 'user-agent: vscode-restclient' \
  --data '{"query":"mutation SignInMutation
  ($signUpEmail: String!, $signUpPassword: String!) {\n  signIn(email: $signUpEmail, password: $signUpPassword)\n}","variables":{"signUpEmail":"admin@admin.com","signUpPassword":"password"}}' | jq



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
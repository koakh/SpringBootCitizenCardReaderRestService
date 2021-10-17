# README

## Ubuntu 20.04 Install

```shell
# update system
$ sudo apt update -y && sudo apt upgrade -y
# autenticacao.gov deps
$ sudo apt install build-essential libpcsclite-dev libpoppler-qt5-dev libzip-dev libopenjp2-7-dev libpng-dev openjdk-11-jdk qtbase5-dev qt5-qmake qtbase5-private-dev qt5-default qtdeclarative5-dev qtquickcontrols2-5-dev qml-module-qtquick-controls2 libssl-dev libxerces-c-dev libxml-security-c-dev swig libcurl4-nss-dev pcscd qml-module-qt-labs-folderlistmodel qml-module-qt-labs-settings qml-module-qt-labs-platform qml-module-qtgraphicaleffects qml-module-qtquick-controls qml-module-qtquick-controls2 qml-module-qtquick-dialogs qml-module-qtquick-layouts qml-module-qtquick-templates2 qml-module-qtquick-window2 qml-module-qtquick2 qt5-gtk-platformtheme libnsspem fonts-lato -y

# check jdk
$ java --version
openjdk 11.0.11 2021-04-20
OpenJDK Runtime Environment (build 11.0.11+9-Ubuntu-0ubuntu2.20.04)
OpenJDK 64-Bit Server VM (build 11.0.11+9-Ubuntu-0ubuntu2.20.04, mixed mode, sharing)

```

## Clone and Build autenticacao.gov

```shell
# clone project
$ cd ~ && git clone https://github.com/koakh/autenticacao.gov.git
Cloning into 'autenticacao.gov'...

# qmake
$ cd ~/autenticacao.gov/pteid-mw-pt/_src/eidmw
$ qmake pteid-mw.pro
# make
$ make
# install
$ sudo make install && sudo ldconfig
# check pteidlibj.jar
$ ls /home/ubuntu/autenticacao.gov/pteid-mw-pt/_src/eidmw/jar/
# check libs
$ ls -la /usr/local/lib/
```

## Insert CardReader

```shell
$ sudo lsusb | grep "0bda:0165"
Bus 001 Device 003: ID 0bda:0165 Realtek Semiconductor Corp. Smart Card Reader Interface
```

## Clone and Build SpringBootCitizenCardReaderRestService

```shell
# checkout
$ mkdir ~/Development && cd ~/Development
$ git clone https://github.com/koakh/SpringBootCitizenCardReaderRestService.git
$ cd SpringBootCitizenCardReaderRestService
$ ./mvnw spring-boot:run

...wait while loading maven dependencies
...
Tomcat started on port(s): 8080 (http) with context path

# test cardReader endpoint
$ curl localhost:8080

# awesome we see in spring log
readerCount 1
true
{ documentVersion='006.007.23', documentType='Cartão de Cidadão', country='PRT', givenName='MÁRIO ALBERTO', surname='MENDES MONTEIRO',...
```

## Configure Debugger

```shell
java.lang.UnsatisfiedLinkError: no pteidlibj in java.library.path: [/usr/java/packages/lib, /usr/lib/x86_64-linux-gnu/jni, /lib/x86_64-linux-gnu, /usr/lib/x86_64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]
```

to configure the debugger we must use `"vmArgs": "-Djava.library.path=/usr/local/lib"` and copy `pteidlibj.jar` to `java.library.path`

```shell
$ mkdir /usr/local/lib/pteid_jni
$ sudo cp /home/mario/autenticacao.gov/pteid-mw-pt/_src/eidmw/jar/pteidlibj.jar /usr/local/lib/pteid_jni
```
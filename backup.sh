#!/bin/bash

DT=$(date +%Y-%m-%d-%H-%M)
DIR=.bak
FILE=$DIR/$DT.tgz
FILE_EXCLUDE=exclude.tag
mkdir $DIR -p
touch .bak/$FILE_EXCLUDE
touch ./frontend/node_modules/$FILE_EXCLUDE
touch ./target/$FILE_EXCLUDE

tar -zcvf $FILE \
	--exclude-tag-all=$FILE_EXCLUDE \
	.

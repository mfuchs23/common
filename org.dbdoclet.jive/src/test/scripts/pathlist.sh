#!/bin/bash
export CLASSPATH=\
../../../kits/jars/dodo-services.jar:\
../build/dodo-jive.jar:\
../build/test/classes

java \
    -Djava.util.logging.config.file=logging.properties \
    org.dbdoclet.jive.test.TestPathList
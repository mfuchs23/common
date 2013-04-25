#!/bin/bash

export CLASSPATH=\
../../assembly/lib/dodo-commons_5.1.4.jar:\
../build/dodo-jive_5.2.0.jar:\
./resource:\
./build/classes

java org.dbdoclet.jive.test.SideBarTest

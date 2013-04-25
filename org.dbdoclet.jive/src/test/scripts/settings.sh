#!/bin/bash

export CLASSPATH=\
../work/assembly/lib/dodo-services.jar:\
../build/dodo-jive.jar:\
../build/test/classes

java org.dbdoclet.jive.test.SettingsTest

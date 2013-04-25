#!/bin/bash

export CLASSPATH=\
../../../assembly/lib/dodo-services.jar:\
../build/dodo-jive.jar:\
../build/test/classes

#java org.dbdoclet.jive.test.ReportBoxTest
java org.dbdoclet.jive.test.ErrorBoxTest
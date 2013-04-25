#!/bin/bash

export CLASSPATH=\
../work/assembly/lib/dodo-services.jar:\
../work/jars/jcalendar/1.3.2/jcalendar-1.3.2.jar:\
../work/jars/jlfgr.jar:\
../build/dodo-jive.jar:\
../build/test/classes

java org.dbdoclet.jive.test.PropertyDialogTest

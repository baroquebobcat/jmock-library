#!/bin/sh
# Damage control build script for jMock.

SRCDIR=src/framework
WEBSITE=${WEBSITE:-dcontrol@www.codehaus.org:/www/jmock.codehaus.org/}

WEBDIR=website/output
JAVADOCDIR=$WEBDIR/docs/javadoc

(cd website; ruby ./skinner.rb)
$JAVA_HOME/bin/javadoc \
	-windowtitle 'jMock API Documentation' \
	-d $JAVADOCDIR \
	-link http://www.junit.org/junit/javadoc/3.8.1/ \
	-link http://java.sun.com/j2se/1.4.2/docs/api/ \
	-sourcepath $SRCDIR \
	-subpackages org.jmock

scp -r $WEBDIR/* $WEBSITE

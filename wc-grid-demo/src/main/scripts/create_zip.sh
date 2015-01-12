#!/bin/bash

package=com.vaadin.prototype.wc
version=1.0-SNAPSHOT
client=wc-client
m2repo=~/.m2/repository/`echo $package | tr "." "/"`
jarName=vaadin-components-0.1.0.jar

pwd=`pwd`
set -xe

createTmp() {
  tmpDir=/tmp/$$
  rm -rf $tmpDir
  mkdir -p $tmpDir
}

copyStaticStuff() {
  cp -r $pwd/src/main/package/* $tmpDir
}

copyGwtStuff() {
  jar=$pwd/../$client/target/$client-$version.jar
  if [ ! -f $jar ]
  then
     jar=`find $m2repo -name $client*.jar | tail -1`
  fi
  mkdir $tmpDir/gwt
  cp $jar $tmpDir/gwt/$jarName
}

copyBowerStuff() {
  mkdir -p $tmpDir/web-components
  cd $tmpDir
  bower install \
    vaadin-components \
    vaadin-grid \
    vaadin-slider \
    vaadin-progress-bar
  cd $pwd
  mv $tmpDir/bower_components/* $tmpDir/web-components/
  rmdir $tmpDir/bower_components
}

copyNgStuff() {
  ngFile=`find $pwd/../$client/src/main/ -name "*ng*.js"`
  mkdir -p $tmpDir/ng
  cp $ngFile $tmpDir/ng/vaadin-ng.js
}

createTmp
copyStaticStuff
copyGwtStuff
copyNgStuff
copyBowerStuff

echo rm -rf $tmpDir





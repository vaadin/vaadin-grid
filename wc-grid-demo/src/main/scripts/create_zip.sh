#!/bin/bash

package=com.vaadin.prototype.wc
version=1.0-SNAPSHOT
client=wc-client
m2repo=~/.m2/repository/`echo $package | tr "." "/"`
pkgName=vaadin-components
jarName=$pkgName-$version.jar
jsName=$pkgName.js

pwd=`pwd`
set -xe

createTmp() {
  tmpDir=/tmp/$$
  rm -rf $tmpDir
  mkdir -p $tmpDir
}

copyStaticStuff() {
  for i in README.txt license.html licenses api examples 
  do
    cp -r $pwd/src/main/package/$i $tmpDir
  done
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

copyThemes() {
  mkdir -p $tmpDir/themes
  cp -r $tmpDir/web-components/vaadin-valo $tmpDir/themes/valo
}

mergeJs() {
  echo "// vaadin components version: $version" > $tmpDir/$jsName
  tail -1 $tmpDir/web-components/webcomponentsjs/webcomponents-lite.min.js >> $tmpDir/$jsName
  grep -v "^<.*>$" $tmpDir/web-components/vaadin-components/vaadin-components.html >> $tmpDir/$jsName
}

copyNgStuff() {
  ngFile=`find $pwd/../$client/src/main/ -name "*ng*.js"`
  mkdir -p $tmpDir/ng
  cp $ngFile $tmpDir/ng/vaadin-ng.js
}

createZip() {
  cd $tmpDir
  content=`ls -1`
  name=$pkgName-$version
  mkdir $name
  mv $content $name
  zip -qr /tmp/$name.zip .
  echo "Created /tmp/$name.zip"
}

removeUnused() {
  find $tmpDir '(' -name .bower.json -o -name .gitignore ')' -delete
}

createTmp
copyStaticStuff
copyGwtStuff
copyNgStuff
copyBowerStuff
copyThemes
mergeJs
removeUnused
createZip

rm -rf $tmpDir


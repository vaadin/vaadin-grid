#!/bin/bash

package=com.vaadin.prototype.wc
[ -n "$1" ] && version=$1 || version=1.0-SNAPSHOT
client=wc-client
gwtapicomponents=wc-polymer-demo
gwtapi=wc-gwt-api
m2repo=~/.m2/repository/`echo $package | tr "." "/"`
pkgName=vaadin-components
jarName=$pkgName-$version.jar
jsName=$pkgName.js

pwd=`pwd`
set -xe

setVersion(){
  perl -pi -e 's,%VERSION%,'$version',g' src/main/package/META-INF/MANIFEST.MF
}

createTmp() {
  tmpDir=/tmp/$$
  rm -rf $tmpDir
  mkdir -p $tmpDir
}

copyStaticStuff() {
  for i in README.txt license.html licenses api examples META-INF
  do
    cp -r $pwd/src/main/package/$i $tmpDir
  done
}

copyGwtStuff() {
  cd $pwd/..
  mvn package -am -pl $gwtapi  -Dgwt.compiler.skip || exit 1
  jar=$pwd/../$gwtapi/target/$gwtapi-$version.jar
  [ ! -f $jar ] && echo Error not found: $jar && exit 1
  mkdir $tmpDir/gwt
  cp $jar $tmpDir/gwt/$jarName
}

copyBowerStuff() {
  mkdir -p $tmpDir/web-components
  cd $tmpDir
  bower cache clean
  bower install \
    vaadin-components \
    vaadin-grid \
    vaadin-slider \
    vaadin-progress-bar
  cd $pwd
  mv $tmpDir/bower_components/* $tmpDir/web-components/
  rmdir $tmpDir/bower_components
  # Not really bower ..
  mkdir -p $tmpDir/web-components/observe-polyfill
  cp $pwd/src/main/package/web-components/observe-polyfill/Object.observe.poly.js $tmpDir/web-components/observe-polyfill
}

copyThemes() {
  mkdir -p $tmpDir/themes
  cp -r $tmpDir/web-components/vaadin-valo $tmpDir/themes/valo
}

mergeJs() {
  echo "// vaadin components version: $version" > $tmpDir/$jsName
  cat $tmpDir/web-components/webcomponentsjs/webcomponents.min.js >> $tmpDir/$jsName
  cat $tmpDir/web-components/observe-polyfill/Object.observe.poly.js >> $tmpDir/$jsName
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

setVersion
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


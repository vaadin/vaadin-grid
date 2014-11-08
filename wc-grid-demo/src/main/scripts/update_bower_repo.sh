#!/bin/sh
#
# This script clones the bower repo for vaadin-x from github,
# updates the vaadin-x.html file with the last compiled stuff,
# updates vaadin themes and gwt themes, update versions and
# tags the project.
#
# Registering a package will make it installable to anyone
# via the registry (https://bower.herokuapp.com), to register
# it we run:
# $ bower register vaadin-x git://github.com/manolo/vaadin-x.git
#
# Deleting the registered project needs to be done by the owner
# $ curl -X DELETE "https://bower.herokuapp.com/packages/vaadin-x?access_token=<token>"
# To know the access token go to https://github.com/settings/applications
#
#

warDir="$1"
modulePath="$1/$2"
version="$3"
gitRepo="$4"
package="$5"
vaadinVersion="$6"
[ -z "$6" ] && echo "Usage $0 <warDir> <modulePath> <version> <gitRepo> <package> <vaadinVersion>" && exit 1
[ ! -d "$warDir" ] && echo "warDir does not exist: $warDir" && exit 1
[ ! -d "$modulePath" ] && echo "modulePath does not exist: $modulePath" && exit 1

## Create a tmp dir and remove it on exit
now=`date +%s`
tmpDir=`mktemp -d /tmp/bower-vaadin-x-$now`
trap "rm -rf $tmpDir" EXIT

## Clone the vaadin web components repo
git clone $gitRepo $tmpDir || exit 1

## Copy stuff from the gwt output dir
cd $modulePath || exit 1
htmlFile=`ls -t1 *-import.html | head -1`
[ ! -f $htmlFile ] && echo "No *-import.html file to upload" && exit 1
cp $htmlFile $tmpDir/$package.html || exit 1

## Copy stuff from the war dir
cp $warDir/index.html $tmpDir/demo.html || exit 1
cp $warDir/ng-vaadin.js $tmpDir || exit 1
tar cf $tmpDir/module.tar \
    gwt/*/*.css \
    deferred \
    index.html \
    >/dev/null 2>&1

## Update version, and extract files to update
cd $tmpDir
perl -pi -e 's,"version"\s*:\s*"[^"]+","version" : "'$version'",' bower.json
tar xf module.tar
rm -f module.tar
perl -pi -e 's,^.*(nocache|<link).*$,,g' demo.html
perl -pi -e 's,</head,  <link rel="import" href="'$package'.html"></link>\n</head,' demo.html

## Attach Vaadin .css theme files
mkdir tmpThemes
cd tmpThemes || exit 1
themesJar=`find ~/.m2/repository/com/vaadin/ -name "vaadin-themes-$vaadinVersion.jar"`
if [ -f $themesJar ]
then
  jar xf $themesJar
  tar cf $tmpDir/themes.tar VAADIN/themes/*/*.css
  cd $tmpDir
  rm -rf tmpThemes
  tar xf themes.tar
  rm -f themes.tar
fi


## Check if something has been modified
if git status  --porcelain | grep . >/dev/null
then
   ## If this version already exists remove it
   if git tag | grep "^v$version$" >/dev/null
   then
      git tag -d v$version || exit 1
      git push origin :refs/tags/v$version || exit 1
   fi

   ## Add new files and commit changes
   git add .
   git commit -m "Upgrading version $version" . || exit 1

   ## If there is something to push, do it
   if ! git diff --cached --exit-code
   then
      git push origin master || exit 1
   fi

   ## Create the version tags
   git tag -a v$version -m "Release $version" || exit 1
   git push origin master --tags || exit 1
fi

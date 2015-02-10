#!/bin/sh
#
# This script takes from this project all the stuff necessary to publish
# a web component in bower. If the component is a gwt one, it compiles
# it previously.
#
# parameters: component_version git_repository web_component_name [optional:gwtModuleName]
#
# NOTES for public bower register:
#   Registering a package will make it installable to anyone
#   via the registry (https://bower.herokuapp.com), to register
#   it we run:
#   $ bower register vaadin-components git://github.com/manolo/vaadin-components.git
#
#   Deleting the registered project needs to be done by the owner
#   $ curl -X DELETE "https://bower.herokuapp.com/packages/vaadin-components?access_token=<token>"
#   To know the access token go to https://github.com/settings/applications
#
# NOTES for private bower register.
#   We are running a private bower server and a private git instance.
#   So you need this configuration in your local bower preferences file:
#   ~/.bowerrc
#   { "registry": "http://vaadin-components.intra.itmill.com:5678" }
#
#   To register the vaadin-components pakage we did run:
#   $ bower register vaadin-grid git://vaadin-components.intra.itmill.com/vaadin-grid.git
#
#   To push changes, maven uses ssh://git@vaadin-components.intra.itmill.com:/opt/git/vaadin-grid.git
#   You need to add your ssh public key to the admin of the vaadin-components internal server.
#

version="$1"; shift
gitUrl="$1"; shift
package="$1"; shift
moduleName=$1

gitRepo="$gitUrl/$package.git"

CheckFolders() {
 ## Just some checks to know that folders are ok
 dir=`dirname $0`
 cd $dir || exit 1
 pwd=`pwd`
 ##
 tmpDir=$pwd/../../target
 [ -d $tmpDir ] && rm -rf $tmpDir
 mkdir -p $tmpDir
 cd $tmpDir || exit 1
}

CompileGwtModule() {
 gwtDir=$pwd/../../../vaadin-components-gwt
 [ ! -d $gwtDir ] && echo "Not found: $gwtDir" && exit 1
 warDir="$gwtDir/target/vaadin-components-gwt-$version/"
 modulePath="$warDir/$moduleName/"
 ## Dont compile if it was already compiled
 if [ ! -d "$modulePath" ]
 then
  cd $gwtDir || exit 1
  mvn package
  [ -d $modulePath ] || exit 1
 fi
}

CloneRepo() {
 ## Create a tmp dir and remove it on exit
 now=`date +%s`
 tmpDir=`mktemp -d /tmp/bower-vaadin-components-$now`
 trap "rm -rf $tmpDir" EXIT

 ## Clone the vaadin web components repo
 git clone $gitRepo $tmpDir || exit 1
}

UpdatePolymerModule() {
 modulePath=$pwd/../../../vaadin-components/$package
 [ ! -d $modulePath ] && echo "Not found: $modulePath" && exit 1
 cd $modulePath || exit 1
 ## For polymer web components we just copy all the stuff
 cp -r * $tmpDir/
}

UpdateGwtModule() {
 ## Copy stuff from the gwt output dir
 cd $modulePath || exit 1
 htmlFile=`ls -t1 *-import.html | head -1`
 [ ! -f $htmlFile ] && echo "No *-import.html file to upload" && exit 1
 cp $htmlFile $tmpDir/$package.html || exit 1

 ## Copy stuff from the war dir
 cp $warDir/demo-$package.html $tmpDir/demo.html || exit 1
 for i in ng-vaadin.js bower.json
 do
   cp $warDir/$i $tmpDir || exit 1
 done

 ## Maybe this module has gwt split points
 tar cf $tmpDir/module.tar deferred >/dev/null 2>&1

 ## Extract files to update
 cd $tmpDir || exit 1
 tar xf module.tar
 rm -f module.tar
 ## Demo files in the gwt project use different links
 perl -pi -e 's,^.*(nocache|<link).*$,,g' demo.html
 perl -pi -e 's,</head,  <link rel="import" href="'$package'.html"></link>\n</head,' demo.html
 perl -pi -e 's,src="bower_components/,src="../,g' demo.html
 ## bower.json file is a generic one without the actual package name
 perl -pi -e 's,^.*'$package'.*$,,g' bower.json
 perl -pi -e 's,"name"\s*:\s*"[^"]+","name" : "'$package'",' bower.json
}

UpdateVersion() {
 cd $tmpDir || exit 1
 perl -pi -e 's,"version"\s*:\s*"[^"]+","version" : "'$version'",' bower.json
 perl -pi -e 's,"private"\s*:\s*[^ \,]+,"private" : false,' bower.json
}

UpdateRepo() {
 cd $tmpDir
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
}

CheckFolders
[ -n "$moduleName" ] && CompileGwtModule
CloneRepo
[ -n "$moduleName" ] && UpdateGwtModule || UpdatePolymerModule
UpdateVersion
UpdateRepo

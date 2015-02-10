#!/bin/sh
# This script is called from the build system, and call the update_component
# script which actually will update the webcompoent and update bower repos.
#
# parameters: components_version git_repository list_of_components
#

version="$1"; shift
gitUrl="$1"; shift
components="$*"

dir=`dirname $0`
script="$dir/update_component.sh"

for component in $components
do
 [ $component = vaadin-grid ] && moduleName=VaadinGrid || moduleName=""
 echo Running: $script $version $gitUrl $component $moduleName
 sh -xe $script $version $gitUrl $component $moduleName || exit 1
done


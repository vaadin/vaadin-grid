#!/bin/sh
warDir="$1"; shift
modulePrefix="$1"; shift
version="$1"; shift
gitUrl="$1"; shift
vaadinVersion="$1"; shift
modules=$*

dir=`dirname $0`
for module in $modules
do
   case $module in
   Demo) package=x-vaadin; gitRepo=$gitUrl/vaadin-x-bower.git ;;
   DemoGrid) package=v-grid; gitRepo=$gitUrl/v-grid.git ;;
   DemoProgress) package=v-progress; gitRepo=$gitUrl/v-progress.git ;;
   DemoSlider) package=v-slider; gitRepo=$gitUrl/v-slider.git ;;
   esac

   modulePath="$warDir/$module"
   $dir/update_bower_repo.sh $warDir $modulePrefix $version $gitRepo $package $vaadinVersion $module
done

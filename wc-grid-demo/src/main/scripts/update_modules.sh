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
   VaadinComponents) package=vaadin-components; gitRepo=$gitUrl/vaadin-components.git; files="bigdata.js" ;;
   VaadinGrid) package=vaadin-grid; gitRepo=$gitUrl/vaadin-grid.git; files="bigdata.js" ;;
   VaadinProgressBar) package=vaadin-progress-bar; gitRepo=$gitUrl/vaadin-progress-bar.git; files="" ;;
   VaadinSlider) package=vaadin-slider; gitRepo=$gitUrl/vaadin-slider.git; files="" ;;
   Themes) package=vaadin-themes; gitRepo=$gitUrl/vaadin-themes.git; files="";;
   esac

   modulePath="$warDir/$module"
   $dir/update_bower_repo.sh $warDir "$modulePrefix" $version $gitRepo $package $vaadinVersion $module $files
done

#!/usr/bin/perl
#
# A script to process polymer and paper webcomponent templates
# and generate JsInterop interfaces to use them in GWT.
#
# Note that this is thought as a prototype to demonstrate that
# we can process templates, but this code is very weak.
#
# @author Manolo Carrasco.
#
use File::Basename;
use strict;

my $package = "package com.vaadin.prototype.wc.gwt.client.components;\n";

#import com.google.gwt.core.client.js.JsArray;
#import com.google.gwt.core.client.js.JsObject;

my $head = "$package
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.*;

";

sub getJavaType {
  my $a = shift;
  return "String" if ($a =~ /string/i );
  return "boolean" if ($a =~ /boolean/i );
  return "JsArray" if ($a =~ /array/i );
  return "Element" if ($a =~ /element/i );
  return "double" if ($a =~ /number/i );
  #return "JsObject" if ($a =~ /object/i );
  return "JavaScriptObject";
}

sub readFile {
  my ($file, $super) = @_;
  return if (!-f $file);

  my $tag = basename($file, '.html');

  my $clz = $tag;
  $clz =~ s/(^|-)(.)/uc($2)/eg;
 
  my $java = "$clz.java";
  return if (!$super && -f $java);
 
  print "Generated element: $tag -> $clz.java\n";
  my $interface = "\@JsType(prototype = \"HTMLElement\", isNative = true)\npublic interface $clz extends HTMLElement";
  # if ($super) {
  #  $interface .= "<T extends $clz<?>>";
  # }
  
  my ($event, $body, $inCom, $comment, $case, $attr, $load, %done, @params);
  my $javatype = "Object";
  my $rtype = "void";

  open(my $f, $file) || die "$! -> $file";
  while(<$f>) {
    if ($inCom && /^\s*\*\/\s*$/ ) {
      $comment .= $_;
      $comment =~ s/\n \s+\*/\n   \*/g;
      $comment =~ s/^\s+\//\n  \//;
      my $ret = $super ? 'T' : $clz;
      $ret = $clz;
      if ($case eq 'attribute' && $done{$attr}) {
      } elsif ($case eq 'element') {
        $interface = "$comment$interface";
      } elsif ($case eq 'method') {
        $body .= $comment;
        my $sign = "";
        my $i = 0;
        foreach my $p (@params) {
          $sign .= ", " if ($sign ne '');
          $sign .= getJavaType($p) . " arg" . ($i++);
        }
        $body .= "  $rtype $attr($sign);\n";
      } elsif ($case eq 'event') {
        $event = "  void addEventListener(String event, EventListener listener);\n" if ("$event" eq '');
        $event = "$comment$event";
        $event =~ s/\s*\*\/\s*\/\*\*\s*\n//smg;
      } elsif ($case eq 'attribute') {
        $done{$attr} = 1;
        $body .= $comment;
        $body .= "  \@JsProperty $ret $attr($javatype val);\n";
        $body .= "  \@JsProperty $javatype $attr();\n";
      } else {
        # print "Unknown Case: $case\n$comment";
      }
      $inCom = 0;
      $comment = "";
    } elsif ($inCom && /^\s*\*\s*\@(attribute|event|element|method)\s+([^\s\r]+)/) {
      $comment .= $_;
      $case = "";
      $case = $1;
      $attr = $2;
    } elsif ($inCom && /^\s*\*\s*\@type\s+(.*)\s*$/) {
      $comment .= $_;
      $javatype = getJavaType($1);
    } elsif ($inCom && /^\s*\*\s*\@param\s+\{*([^ \}]+).*$/) {
      $comment .= $_;
      push @params, $1;
    } elsif ($inCom && /^\s*\*\s*\@returns\s+\{*([^ \}]+).*$/) {
      $comment .= $_;
      $rtype = getJavaType($1);
    } elsif ($inCom && /^\s*\*\s*.*$/) {
      $comment .= $_;
    } elsif (!$inCom && /^\s*\/\*\*\s*$/ ) {
      $comment .= $_;
      @params = ();
      $rtype = "void";
      $inCom = 1;
    } elsif (/^\s*$/) {
    } elsif (/"(import)".*href="[^"]*?([^\/"]+\-[^\/"]+).html"/
          || /^.*(extends)="([^"]+)".*attributes="([^"]+)".*$/
          || /^\s*\@(extends)\s+([^\s]+)/
          ){
      my $dir = dirname($file);
      my ($oper, $nfile) = ($1, $2);
      my $iface = $nfile;
      $iface =~ s/(^|-)(.)/uc($2)/eg;

      if ($oper eq 'import') {
        $load .= ", " if ($load ne '');
        $load .= "$iface.class";
        next;
      }
      
      next if ($interface =~ /, $iface/);
      $interface = "$interface, $iface";
    
      # $interface .= "<$clz>";

      $nfile = "$dir/../$nfile/$nfile.html";
      readFile($nfile, $clz);
    } else {
      $inCom = 0;
      $comment = "";
      $case = "";
    }
  }
  close($f);
  # return if ($body eq '');
  open(my $f, ">$clz.java");
  $load = "\n  Class<?>[] dependencies = new Class<?>[]{$load};\n";
  print $f "$head$interface \{\n$load$event$body\}\n";
  close($f);
}

### Go through */*-*.html files in current folder
my $basedir = ".";
opendir(my $dh, $basedir) || die "$! -> $basedir";
while(readdir $dh) {
  if (-d $_) {
     my $folder = $_;
     opendir(my $sd, $folder);
     while (readdir $sd) {
       readFile "$folder/$_" if (/.*-.*\.html$/);
     }
     closedir($sd);
  }
}
closedir($dh);

### Generate Icons interface
my $icons = "$package\npublic interface Icon {\n";
my $allicons = "  public static String[] ALL = new String[]{";
sub readIcons {
  my $file = shift;
  my $name = basename($file, ".html");
  $name =~ s/icons$//;
  $name =~ s/-/:/;
  open(my $f, $file) || die "$! -> $file";
  while(<$f>) {
    if (/^\s*<g\s+id="([^"]+)"/) {
      my $icon = "$name$1";
      my $var = uc($icon);
      $var =~ s/[^\w]+/_/g;
      $icons.= "  public static final String $var = \"$icon\";\n";
      $allicons .= "\n   $var,";
    }
  }
  close($f);
}

my $icondir = "core-icons/iconsets/";
opendir(my $dh, $icondir) || die "$! -> $icondir";
while(readdir $dh) {
  readIcons("$icondir$_") if (/.html$/);
}
closedir($dh);

print "Generated Icon.java\n";
open(my $f, ">Icon.java");
$allicons =~ s/,$//;
print $f "$icons$allicons};\n}\n";
close($f);

# @group Polymer Core Elements
# @element core-input
# @homepage github.io

# @event change

# @type Array
# @type Boolean
# @type Element
# @type Object
# @type array
# @type boolean
# @type number
# @type number|'fit'
# @type object
# @type string
# @type string (JSON)
# @type string start|center|end
# @type string start|center|end|between
# @type string|RegExp|Function(value)
# @type String


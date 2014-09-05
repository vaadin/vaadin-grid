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

my $head = 'package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;

';

sub readFile {
  my ($file, $super) = @_;
  return if (!-f $file);
  my $tag = basename($file, '.html');
  my $clz = $tag;
  $clz =~ s/(^|-)(.)/uc($2)/eg;
  my $java = "$clz.java";
  return if (!$super && -f $java);
  print "$tag $clz\n";
  my $interface = "\@JsType(prototype = \"HTMLElement\", isNative = true)\npublic interface $clz";
  # if ($super) {
  #  $interface .= "<T extends $clz<?>>";
  # }
  my $event = "";
  my $body = "";
  open(my $f, $file) || die "$! -> $file";
  my %done;
  my $inCom;
  my $comment;
  my $case;
  my $attr;
  my $javatype = "Object";
  while(<$f>) {
    if ($inCom && /^\s*\*\/\s*$/ ) {
      $comment .= $_;
      $comment =~ s/\n \s+\*/\n   \*/g;
      $comment =~ s/^\s+\//\n  \//;
      my $ret = $super ? 'T' : $clz;
      $ret = $clz;
      print "$case\n";
      if ($case eq 'attribute' && $done{$attr}) {
      } elsif ($case eq 'element') {
        $interface = "$comment$interface";
      } elsif ($case eq 'method') {
        $body .= $comment;
        $body .= "  void $attr();\n";
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
        print "Unknown Case: $case\n$comment";
      }
      $inCom = 0;
      $comment = "";
    } elsif ($inCom && /^\s*\*\s*\@(attribute|event|element|method)\s+(.*)\s*$/) {
      $comment .= $_;
      $case = "";
      $case = $1;
      $attr = $2;
    } elsif ($inCom && /^\s*\*\s*\@type\s+(.*)\s*$/) {
      $comment .= $_;
      $javatype = "String" if ($1 =~ /string/i );
      $javatype = "boolean" if ($1 =~ /boolean/i );
      $javatype = "JsArray" if ($1 =~ /array/i );
      $javatype = "Element" if ($1 =~ /element/i );
      $javatype = "JsObject" if ($1 =~ /object/i );
      $javatype = "double" if ($1 =~ /number/i );
    } elsif ($inCom && /^\s*\*\s*.*$/) {
      $comment .= $_;
    } elsif (!$inCom && /^\s*\/\*\*\s*$/ ) {
      $comment .= $_;
      $inCom = 1;
    } elsif (/^\s*$/) {
    } elsif (/^.*extends="([^"]+)".*attributes="([^"]+)".*$/) {
      my $dir = dirname($file);
      my $nfile = $1;
      my $iface = $nfile;

      $iface =~ s/(^|-)(.)/uc($2)/eg;
      next if ($interface =~ / $iface(,$)/);

      if ($interface =~ /$clz extends /) {
        $interface = "$interface, $iface";
      } else {
        $interface = "$interface extends $iface";
      }
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
  return if ($body eq '');
  open(my $f, ">$clz.java");
  print $f "$head$interface \{\n$event$body\}\n";
  close($f);
}

foreach (@ARGV) {
  readFile $_;
}

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


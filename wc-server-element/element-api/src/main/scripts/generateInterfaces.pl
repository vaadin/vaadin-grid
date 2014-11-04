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
use File::Path qw/make_path/;
use strict;

my $ns = "com.vaadin.prototype.wc";

my $basedir = shift || ".";
my $bdir= shift || ".";

print "B $bdir BS $basedir\n";

#import com.google.gwt.core.client.js.JsArray;
#import com.google.gwt.core.client.js.JsObject;

my $interface_tpl = 'package __PKG__;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;
import __NS__.gwt.client.html.*;

@JsType(prototype = "HTMLElement", isNative = true)
public interface __CLZ__ extends HTMLElement __INTERFACES__ {
  Class<?>[] dependencies = new Class<?>[]{__LOAD__};
__EVENT__
__CONTENT__
}
';

my $state_tpl = 'package __PKG__;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class __CLZ__State extends __EXTENDS__ {
__CONTENT__
}
';

my $connector_tpl = 'package __PKG__;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import __NS__.gwt.client.components.__CLZ__Widget;
import __NS__.server.ui.__CLZ__Component;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(__CLZ__Component.class)
public class __CLZ__Connector extends __EXTENDS__ {

    public IsProperties stateProperties() {
__SET_STATES__
      
      IsProperties p = super.stateProperties();
__SET_PROPS__
      return p;
    }

    @Override
    public __CLZ__Widget getWidget() {
        return (__CLZ__Widget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
__SET_WIDGET__
    }

    @Override
    public __CLZ__State getState() {
        return (__CLZ__State)super.getState();
    }
}
';

my $widget_tpl = 'package __PKG__;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import __NS__.gwt.client.*;

public class __CLZ__Widget extends __EXTENDS__  {
  
    protected String[] events() {
      return new String[]{__EVENTS__};
    }
    
    public __CLZ__Widget() {
      super(WC.create(__CLZ__.class));
    }
    
    public __CLZ__Widget(__CLZ__ element) {
      super(element);
    }
    
    protected __CLZ__ element() {
      return (__CLZ__)super.getElement();
    }

__CONTENT__
}
';

my $component_tpl = 'package __PKG__;

import __NS__.gwt.client.ui.__CLZ__State;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class __CLZ__Component extends __EXTENDS__ {
    protected String[] events() {
      return concat(super.events(), new String[]{__EVENTS__});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{__ATTRS__});
    }
  
    @Override
    protected __CLZ__State getState() {
        return (__CLZ__State) super.getState();
    }

__CONTENT__
}';

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

sub getDefault {
  my $t = shift;
  my $d = shift;
  if ($t eq 'String') {
    $d =~ s/[\"\']+//g;
    return "\"$d\"";
  }
  if ($t eq 'boolean') {
    return ($d ne '') ? "$d" : "false";
  }
  if ($t eq 'double') {
    $d =~ s/[^\d\.\-\+]//g;
    return ($d ne '') ? "$d" : "0";
  }
  return "null";
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
  my $interface = "";
  # if ($super) {
  #  $interface .= "<T extends $clz<?>>";
  # }
  
  my ($event, $attrs, $elemcontent, $statecontent, $setstates, $setprops, $setwidget, $wgevents, $wgcontent, $wextend, $compcontent, $inCom, $comment, $case, $attr, $load, $default, %done, @params);
  my $javatype = "Object";
  my $rtype = "void";
  my $state = $state_tpl;

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
        $elemcontent .= $comment;
        my $sign = "";
        my $i = 0;
        foreach my $p (@params) {
          $sign .= ", " if ($sign ne '');
          $sign .= getJavaType($p) . " arg" . ($i++);
        }
        $elemcontent .= "  $rtype $attr($sign);\n";
      } elsif ($case eq 'event') {
        $event = "  void addEventListener(String event, EventListener listener);\n" if ("$event" eq '');
        $event = "$comment$event";
        $event =~ s/\s*\*\/\s*\/\*\*\s*\n//smg;
        $wgevents .= "," if($wgevents ne '');
        $wgevents .= "\"$attr\"";        
      } elsif ($case eq 'attribute') {
        $done{$attr} = 1;
        $elemcontent .= $comment;
        $elemcontent .= "  \@JsProperty $ret $attr($javatype val);\n";
        $elemcontent .= "  \@JsProperty $javatype $attr();\n";
        $wgcontent .= "    public void $attr($javatype val) {\n        element().$attr(val);\n    }\n";
        $wgcontent .= "    public $javatype $attr() {\n        return element().$attr();\n    }\n";
        $attrs .= "," if($attrs ne '');
        $attrs .= "\"$attr\"";
        if ($javatype !~ /^Element|JavaScriptObject|JsArray$/) {
          $setstates .= "                getState().$attr = getWidget().$attr();\n";
          $setwidget .= "            getWidget().$attr(getState().$attr);\n";
          $statecontent .= "  public $javatype $attr = " . getDefault ($javatype, $default) . ";\n";
          $compcontent .= "$comment    public void $attr($javatype val) {\n        getState().$attr = val;\n    }\n";
          $compcontent .= "$comment    public $javatype $attr() {\n        return getState().$attr;\n    }\n";
          $setprops .= "    p.set(\"$attr\", getState().$attr);\n";
        }
      } else {
        # print "Unknown Case: $case\n$comment";
      }
      $inCom = 0;
      $comment = "";
    } elsif ($inCom && /^\s*\*\s*\@(attribute|event|element|method)\s+([^\s\r]+)/) {
      $comment .= $_;
      $case = $1;
      $attr = $2;
    } elsif ($inCom && /^\s*\*\s*\@type\s+(.*)\s*$/) {
      $comment .= $_;
      $javatype = getJavaType($1);
    } elsif ($inCom && /^\s*\*\s*\@default\s+(.*)\s*$/) {
      $default = $1;
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
      
      next if ($iface eq 'CoreThemeAware' || $interface =~ /, $iface/);
      $interface = "$interface, $iface";
      $wextend = "$iface" if ($wextend eq '');
    
      # $interface .= "<$clz>";

      $nfile = "$dir/../$nfile/$nfile.html";
      readFile($nfile, $clz);
    } else {
      $inCom = 0;
      $comment = "";
      $case = "";
    }
  }
  
  my ($body, $dir);
  
  close($f);
  # return if ($elemcontent eq '');
  
  $dir = "$bdir/gwt/client/components/";
  make_path("$dir");
  open(my $f, ">$dir$clz.java");
  $body = $interface_tpl;
  $body =~ s/__PKG__/$ns.gwt.client.components/g;
  $body =~ s/__NS__/$ns/g;
  $body =~ s/__CLZ__/$clz/g;
  $body =~ s/__INTERFACES__/$interface/g;
  $body =~ s/__LOAD__/$load/g;
  $body =~ s/__EVENT__/$event/g;
  $body =~ s/__CONTENT__/$elemcontent/g;
  print $f "$body";
  close($f);
  
  open(my $f, ">$dir${clz}Widget.java");
  $body = $widget_tpl;
  $body =~ s/__NS__/$ns/g;
  $body =~ s/__PKG__/$ns.gwt.client.components/g;
  $body =~ s/__CLZ__/$clz/g;
  if ($wextend ne '') {
    $body =~ s/__EXTENDS__/${wextend}Widget/g ;
  } else {
    $body =~ s/__EXTENDS__/BaseWidget/g ;
  }
  $body =~ s/__EVENTS__/$wgevents/g;
  $body =~ s/__CONTENT__/$wgcontent/g;
  print $f "$body";
  close($f);
  
  $dir = "$bdir/gwt/client/ui/";
  make_path("$dir");
  open(my $f, ">$dir${clz}State.java");
  $body = $state_tpl;
  $body =~ s/__PKG__/$ns.gwt.client.ui/g;
  $body =~ s/__CLZ__/$clz/g;
  $body =~ s/__CONTENT__/$statecontent/g;
  if ($wextend ne '') {
    $body =~ s/__EXTENDS__/${wextend}State/g ;
  } else {
    $body =~ s/__EXTENDS__/AbstractFieldState/g ;
  }
  print $f "$body";
  close($f);
  
  open(my $f, ">$dir${clz}Connector.java");
  $body = $connector_tpl;
  $body =~ s/__PKG__/$ns.gwt.client.ui/g;
  $body =~ s/__NS__/$ns/g;
  $body =~ s/__CLZ__/$clz/g;
  $body =~ s/__SET_STATES__/$setstates/g;
  $body =~ s/__SET_WIDGET__/$setwidget/g;
  $body =~ s/__SET_PROPS__/$setprops/g;
  if ($wextend ne '') {
    $body =~ s/__EXTENDS__/${wextend}Connector/g ;
  } else {
    $body =~ s/__EXTENDS__/BaseConnector/g ;
  }
  print $f "$body";
  close($f);
  
  $dir = "$bdir/server/ui/";
  make_path("$dir");
  open(my $f, ">$dir${clz}Component.java");
  $body = $component_tpl;
  $body =~ s/__PKG__/$ns.server.ui/g;
  $body =~ s/__NS__/$ns/g;
  $body =~ s/__CLZ__/$clz/g;
  $body =~ s/__EVENTS__/$wgevents/g;
  $body =~ s/__ATTRS__/$attrs/g;
  $body =~ s/__CONTENT__/$compcontent/g;
  if ($wextend ne '') {
    $body =~ s/__EXTENDS__/${wextend}Component/g ;
  } else {
    $body =~ s/__EXTENDS__/BaseComponent/g ;
  }
  print $f "$body";
  close($f);
}

### Go through */*-*.html files in current folder
print "Searching for WC in $basedir ...\n";
opendir(my $dh, $basedir) || die "$! -> $basedir";
while(readdir $dh) {
  if (-d "$basedir/$_") {
     my $folder = $_;
     opendir(my $sd, "$basedir/$folder");
     while (readdir $sd) {
       readFile "$basedir/$folder/$_" if (/.*-.*\.html$/);
     }
     closedir($sd);
  }
}
closedir($dh);

### Generate Icons interface
my $icons = "package $ns.gwt.client.components;\npublic interface Icon {\n";
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

my $icondir = "$basedir/core-icons/iconsets/";
opendir(my $dh, $icondir) || die "$! -> $icondir";
while(readdir $dh) {
  readIcons("$icondir$_") if (/.html$/);
}
closedir($dh);

my $dir = "$bdir/gwt/client/components/";
make_path($dir);
print "Generated ${dir}Icon.java\n";
open(my $f, ">${dir}Icon.java");
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


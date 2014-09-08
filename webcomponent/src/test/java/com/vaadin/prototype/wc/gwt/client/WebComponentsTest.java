package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.core.client.js.Js.*;

import java.util.ArrayList;

import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsArrayLike;
import com.google.gwt.core.client.js.JsMapLike;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Document;
import com.google.gwt.junit.client.GWTTestCase;
/**
 * Test class for WebComponents entry-point
 */
public class WebComponentsTest extends GWTTestCase {

  public String getModuleName() {
    return "com.vaadin.prototype.wc.gwt.WebComponents";
  }

  public void disabled_testOnModuleLoad() {
    Demo a = new Demo();
    a.onModuleLoad();
    assertFalse($("#hi").isEmpty());
  }
  
  @JsType
  interface Node {
  }

  @JsType
  interface NodeList<T extends Node> extends JsArrayLike<T> {
  }

  @JsType
  interface Element extends Node {
    NodeList<Element> getElementsByTagName(String tagName);
    void appendChild(Node n);
    @JsProperty
    Element innerHTML(String html);
    @JsProperty
    String innerText();
  }

  @JsType
  interface HTMLDocument extends Element {
    <T extends Element> T createElement(String tagName);
  }

  @Override
  protected void gwtSetUp() throws Exception {
  }

  public void testString() {
    assertEquals("Hello2", js("'Hello2'"));
  }

  public void testStringWithArgs() {
    assertEquals("Hello World", js("'Hello' + ' ' + $0", "World"));
  }

  public void testPrimitiveInt() {
    assertTrue(43 == jsInt("43"));
  }

  public void testPrimitiveInWithIntArg() {
    assertTrue(42 == jsInt("41 + $0", 1));
  }

  public void testDocument() {
    assertEquals(Document.get(), js("$doc"));
  }

  public void testStatements() {
    assertEquals(42,  jsInt("var x = 41; return x + 1;"));
  }

  public void testBlock() {
    int result = jsInt("if ($0) { return $1; } else { return $2; }", true, 42, 99);
    assertEquals(42, result);
  }

  public void testInterop() {
    JsArray array = js("Object.keys({foo: 1, bar: 2})");
    assertEquals(2, array.length());

    JsMapLike map = js("({foo: 1, bar: 2})");
    assertEquals(2, map.intAt("bar"));
  }

  public void testDOM() {
    HTMLDocument doc = js("$doc");
    NodeList<Element> query =
        doc.createElement("div").innerHTML("<ul><li><li>hello</li><li></ul>")
            .getElementsByTagName("li");
    assertEquals(3, query.length());
    assertEquals("hello", query.at(1).innerText());
    boolean found = false;
    for (Element x : query) {
      if (x.innerText().equals("hello")) {
        found = true;
        break;
      }
    }

    assertTrue(found);
  }

  public void testFastArrayListIteration() {
    ArrayList<String> al = new ArrayList<String>();
    al.add("Apples");
    al.add("Oranges");
    al.add("Grapes");
    String result = "";
    for (String x : al) {
      result += x;
    }
    assertEquals("ApplesOrangesGrapes", result);
  }

  public void testJsArrayLiteral() {
    JsArray array = array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    int sum = 0;
    for (int i = 0; i < array.length(); i++) {
      sum += array.intAt(i);
    }
    assertEquals(55, sum);
  }

  public void testJsMapLiteral() {
    JsMapLike<String> map = map("Alice", 42, "Bert", 21, "Candice", 37);
    assertEquals(42, map.intAt("Alice"));
    assertEquals(21, map.intAt("Bert"));
    assertEquals(37, map.intAt("Candice"));
  }
}

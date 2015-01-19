package com.vaadin.prototype.wc.gwt.client.test;

public class ATesting {
    public void gwtSetUp() {
//      ScriptInjector.fromString("function MyJsInterface() {}\n" +
//        "MyJsInterface.prototype.sum = function sum(bias) { return this.x + this.y + bias; }\n" +
//        "MyJsInterface.prototype.go = function(cb) { cb('Hello'); }")
//          .setWindow(ScriptInjector.TOP_WINDOW).inject();
//      ScriptInjector.fromString("function MyJsInterface() {}\n" +
//          "MyJsInterface.prototype.sum = function sum(bias) { return this.x + this.y +   bias; }\n")
//          .inject();
      patchPrototype(MyClassImpl.class);
    }
    
    public void testJsProperties() {
//        MyClassImpl mc = new MyClassImpl();
//        // test both fluent and non-fluent accessors
//        mc.x(-mc.x()).setY(0);
//        assertEquals(58, mc.sum(0));
        // TODO(cromwellian): Add test cases for property overriding of @JsProperty methods in java object
      }

    private void assertEquals(int i, int sum) {
        System.err.println("EQUALS ? " + i + " " + sum);
    }
    
    /**
     * Workaround for the fact that the script is injected after defineClass() has been called.
     */
    private native void patchPrototype(Class<MyClassImpl> myClass) /*-{
        @java.lang.Class::getPrototypeForClass(Ljava/lang/Class;)(myClass).prototype = $wnd.MyClass;
    }-*/;
}

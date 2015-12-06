package com.vaadin.elements.common.js;

import jsinterop.annotations.JsFunction;

@JsFunction
public interface JSFunction<RET, ARG> {
    RET f(ARG arg);
}

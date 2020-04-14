package org.dashbuilder.client.jsinterop;

import elemental2.dom.RequestInit;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

public interface RequestInitFactory {

    public static RequestInit create() {
        return Js.uncheckedCast(JsPropertyMap.of());
    }

}
package com.facuu16.translator.util;

public class Objects {

    private Objects() {
        throw new UnsupportedOperationException();
    }

    public static <V> V notNullOrElse(V obj, V supplier) {
        return obj != null ? obj : supplier;
    }

}

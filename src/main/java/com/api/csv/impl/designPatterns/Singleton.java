package com.api.csv.impl.designPatterns;

public class Singleton {

    private final Singleton instance = new Singleton();

    private Singleton() {

    }

    private Singleton getSingleInstance() {
        return instance;
    }
}

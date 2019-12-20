package com.karateman2400.discordbotcore.modules.example;


import com.karateman2400.discordbotcore.internal.configs.ConfigClass;

public class ExampleConfig extends ConfigClass {

    private String exampleString;

    public ExampleConfig() {}

    public void setExampleString(String exampleString) {
        this.exampleString = exampleString;
    }

    public String getExampleString() {
        return exampleString;
    }
}

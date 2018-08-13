package io.fnproject.example;

import java.util.Date;

public class HelloFunction {

    public String handleRequest(String input) {

        String name = (input == null || input.isEmpty()) ? "world" : input;
        System.err.println("Greeted " + name + " on "+ new Date().toString());
        return "Hello there," + name;
    }

}

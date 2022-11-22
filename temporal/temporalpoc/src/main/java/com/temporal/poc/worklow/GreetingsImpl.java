package com.temporal.poc.worklow;

public class GreetingsImpl implements Greetings{

    @Override
    public String greetWorkflow(String name) {

        return "Hello "+ name;
    }

    @Override
    public void setName(String name) {
        name = "Dude";
    }
}

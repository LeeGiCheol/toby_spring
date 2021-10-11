package me.gicheol.learningtest.spring.bean;

public class ConsolePrinter implements Printer {

    public void print(String message) {
        System.out.println(message);
    }

}

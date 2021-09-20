package me.gicheol.learningtest.calc;

public interface LineCallback<T> {

    T doSomethingWithLine(String line, T value);

}

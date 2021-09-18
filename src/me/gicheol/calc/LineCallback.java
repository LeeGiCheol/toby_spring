package me.gicheol.calc;

public interface LineCallback<T> {

    T doSomethingWithLine(String line, T value);

}

package me.gicheol.learningtest.jdk.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProxyTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("CHEEOLEE"), is("Hello CHEEOLEE"));
        assertThat(hello.sayHi("CHEEOLEE"), is("Hi CHEEOLEE"));
        assertThat(hello.sayThankYou("CHEEOLEE"), is("Thank You CHEEOLEE"));
    }

    @Test
    public void simpleProxyUpperCase() {
        Hello hello = new HelloUppercase(new HelloTarget());
        assertThat(hello.sayHello("cheeolee"), is("HELLO CHEEOLEE"));
        assertThat(hello.sayHi("cheeolee"), is("HI CHEEOLEE"));
        assertThat(hello.sayThankYou("cheeolee"), is("THANK YOU CHEEOLEE"));
    }

    @Test
    public void dynamicProxyUpperCase() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(),
                                                            new Class[] { Hello.class }, new UppercaseHandler(new HelloTarget()));
        assertThat(proxiedHello.sayHello("cheeolee"), is("HELLO CHEEOLEE"));
        assertThat(proxiedHello.sayHi("cheeolee"), is("HI CHEEOLEE"));
        assertThat(proxiedHello.sayThankYou("cheeolee"), is("THANK YOU CHEEOLEE"));
    }

}
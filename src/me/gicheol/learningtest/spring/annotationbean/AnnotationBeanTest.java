package me.gicheol.learningtest.spring.annotationbean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AnnotationBeanTest {

    @Test
    public void simpleBeanScanning() {
        ApplicationContext context = new AnnotationConfigApplicationContext("me.gicheol.learningtest.spring.annotationbean");
        AnnotatedHello annotatedHello = context.getBean("myAnnotatedHello", AnnotatedHello.class);

        assertThat(annotatedHello, is(notNullValue()));
    }

}

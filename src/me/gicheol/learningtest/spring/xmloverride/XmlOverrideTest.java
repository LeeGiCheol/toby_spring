package me.gicheol.learningtest.spring.xmloverride;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class XmlOverrideTest {

    @Test
    public void xmlOverride() {
        String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()));

        ApplicationContext parent = new GenericXmlApplicationContext(basePath + "/parentContext.xml");
        GenericApplicationContext child = new GenericApplicationContext(parent);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions(basePath + "/childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));
    }

    @Test
    public void helloOverride() {
        String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()));

        ApplicationContext parent = new GenericXmlApplicationContext(basePath + "/parentContext.xml");
        GenericApplicationContext child = new GenericApplicationContext(parent);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions(basePath + "/childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        hello.print();
        assertThat(printer.toString(), is("Hello Child"));
    }

}

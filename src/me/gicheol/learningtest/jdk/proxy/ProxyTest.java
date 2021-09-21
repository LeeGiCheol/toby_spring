package me.gicheol.learningtest.jdk.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

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

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertThat(proxiedHello.sayHello("cheeolee"), is("HELLO CHEEOLEE"));
        assertThat(proxiedHello.sayHi("cheeolee"), is("HI CHEEOLEE"));
        assertThat(proxiedHello.sayThankYou("cheeolee"), is("THANK YOU CHEEOLEE"));
    }

    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String) methodInvocation.proceed();
            return ret.toUpperCase();
        }

    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertThat(proxiedHello.sayHello("cheeolee"), is("HELLO CHEEOLEE"));
        assertThat(proxiedHello.sayHi("cheeolee"), is("HI CHEEOLEE"));
        assertThat(proxiedHello.sayThankYou("cheeolee"), is("Thank You cheeolee"));
    }

    @Test
    public void classNamePointcutAdvisor() {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };

        classMethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget {}
        checkAdviced(new HelloWorld(), classMethodPointcut, false);

        class HelloTargetTrue extends HelloTarget {}
        checkAdviced(new HelloTargetTrue(), classMethodPointcut, true);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();

        if (adviced) {
            assertThat(proxiedHello.sayHello("cheeolee"), is("HELLO CHEEOLEE"));
            assertThat(proxiedHello.sayHi("cheeolee"), is("HI CHEEOLEE"));
            assertThat(proxiedHello.sayThankYou("cheeolee"), is("Thank You cheeolee"));
        } else {
            assertThat(proxiedHello.sayHello("cheeolee"), is("Hello cheeolee"));
            assertThat(proxiedHello.sayHi("cheeolee"), is("Hi cheeolee"));
            assertThat(proxiedHello.sayThankYou("cheeolee"), is("Thank You cheeolee"));
        }
    }


}

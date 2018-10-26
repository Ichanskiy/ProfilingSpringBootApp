package com.ichanskiy.profiling.handlers;

import com.ichanskiy.profiling.annotation.Profiling;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> beanNamesWithProfiling = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            beanNamesWithProfiling.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beanNamesWithProfiling.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Profiling " + method.getClass().getPackage().toString());
                    long before = System.nanoTime();
                    Object returnValue = method.invoke(bean, args);
                    long after = System.nanoTime();
                    System.out.println("Full time = " + (after - before));
                    System.out.println("End Profiling!");
                    return returnValue;
                }
            });
        }
        return bean;
    }
}

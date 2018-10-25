package com.ichanskiy.profiling.handlers;

import com.ichanskiy.profiling.annotation.Profiling;
import com.ichanskiy.profiling.controller.ProfilingHandlerController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> beanNamesWithProfiling = new HashMap<>();
    private ProfilingHandlerController controller = new ProfilingHandlerController();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            beanNamesWithProfiling.put(beanName, beanClass);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beanNamesWithProfiling.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isProfiling) {
                        System.out.println("Profiling...");
                        long before = System.nanoTime();
                        Object returnValue = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println(after - before);
                        System.out.println("End Profiling!");
                        return returnValue;
                    }
                    return method.invoke(bean, args);
                }
            });
        }
        return null;
    }
}

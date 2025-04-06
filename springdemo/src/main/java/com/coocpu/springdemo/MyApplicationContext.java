package com.coocpu.springdemo;

import com.coocpu.springdemo.spring.BeanDefinition;
import com.coocpu.springdemo.spring.Component;
import com.coocpu.springdemo.spring.ComponentScan;
import com.coocpu.springdemo.spring.Scope;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auth Felix
 * @since 2025/3/15 15:12
 */
public class MyApplicationContext {

    private Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public  MyApplicationContext(Class cls) throws ClassNotFoundException, NoSuchMethodException {
        if (!cls.isAnnotationPresent(ComponentScan.class)) {
            return;
        }
        ComponentScan declaredAnnotation = (ComponentScan) cls.getDeclaredAnnotation(ComponentScan.class);
        String path = declaredAnnotation.value();
        System.out.println("path = "+path);


        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();

        Thread.currentThread().setContextClassLoader(classLoader);

        String newPath = path.replace(".", "/");
        System.out.println("newPath = " + newPath);
        URL resource = classLoader.getResource(newPath);
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                System.out.println(listFile);
                String filePahtName = newPath + "/" + listFile.getName();
                System.out.println("filePahtName = " + filePahtName);
                Class<?> aClass = classLoader.loadClass(listFile.getName());

//                Class<?> aClass = classLoader.loadClass(listFile.getAbsolutePath().substring());
                if (aClass.isAnnotationPresent(Component.class)) {

                    Component componentAnnotation = aClass.getDeclaredAnnotation(Component.class);
                    String beanName = componentAnnotation.value();


                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setClazz(aClass);
                    if (aClass.isAnnotationPresent(Scope.class)) {
                        Scope scope = aClass.getDeclaredAnnotation(Scope.class);
                        beanDefinition.setScope(scope.value());
                    } else {
                        beanDefinition.setScope("singleInstance");
                    }

                    beanDefinitionMap.put(beanName, beanDefinition);
//                    Object o = aClass.getDeclaredConstructor(aClass).newInstance();

                }
            }
        }

        beanDefinitionMap.forEach((k,v)-> {
            if (v.getScope().equalsIgnoreCase("singleInstance")) {
                Object bean = createBean(v);
                if (null != bean) {
                    beanMap.put(k, bean);
                }
            }
        });

    }

    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleInstance")) {
                Object o = beanMap.get(beanName);
                return o;
            } else { //创建
                return createBean(beanDefinition);
            }
        } else {
            throw new NullPointerException();
        }
    }

    public Object createBean(BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        Object o = null;
        try {
            o = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return o;
    }
}

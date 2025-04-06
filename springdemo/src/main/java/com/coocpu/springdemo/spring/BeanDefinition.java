package com.coocpu.springdemo.spring;

/**
 * @auth Felix
 * @since 2025/3/15 15:42
 */
public class BeanDefinition {
    private String scope;
    private Class clazz;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}

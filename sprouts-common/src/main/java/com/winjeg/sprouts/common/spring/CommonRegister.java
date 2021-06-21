package com.winjeg.sprouts.common.spring;

import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.util.StringUtils;

/**
 * this contains the common methods to register beans
 *
 * @author gongwenjie09147
 */
public interface CommonRegister {

    /**
     * register beans to spring's bean factory
     *
     * @param registry the registry of the bean
     * @param clz      class of the bean to be registered
     */
    default void registerBean(BeanDefinitionRegistry registry, Class<?> clz) {
        String beanName = StringUtils.uncapitalize(clz.getSimpleName());
        registerBean(registry, clz, beanName);
    }

    /**
     * register beans to spring's bean factory
     *
     * @param registry the registry of the bean
     * @param clz      class of the bean to be registered
     * @param beanName name of the bean
     */
    default void registerBean(BeanDefinitionRegistry registry, Class<?> clz, String beanName) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(clz);
        val args = new ConstructorArgumentValues();
        beanDefinition.setConstructorArgumentValues(args);
        try {
            // in case that you've already registered this bean
            registry.registerBeanDefinition(beanName, beanDefinition);
        } catch (BeansException ignored) {
        }
    }

    /**
     * register beans to spring's bean factory
     *
     * @param registry the registry of the bean
     * @param clz      class of the bean to be registered
     * @param cs       constructor arg values
     */
    default void registerBeanWithArgs(BeanDefinitionRegistry registry, Class<?> clz, Object... cs) {
        String beanName = StringUtils.uncapitalize(clz.getSimpleName());
        registerBeanWithArgs(registry, clz, beanName, cs);
    }


    /**
     * register beans to spring's bean factory
     *
     * @param registry the registry of the bean
     * @param clz      class of the bean to be registered
     * @param cs       constructor arg values
     * @param beanName name of then bean
     */
    default void registerBeanWithArgs(BeanDefinitionRegistry registry, Class<?> clz, String beanName, Object... cs) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(clz);
        val args = new ConstructorArgumentValues();
        for (val a : cs) {
            args.addGenericArgumentValue(a);
        }
        beanDefinition.setConstructorArgumentValues(args);
        try {
            // in case that you've already registered this bean
            registry.registerBeanDefinition(beanName, beanDefinition);
        } catch (BeansException ignored) {
        }
    }
}

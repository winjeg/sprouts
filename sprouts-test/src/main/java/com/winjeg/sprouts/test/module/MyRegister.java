package com.winjeg.sprouts.test.module;

import com.winjeg.sprouts.common.spring.SproutsRegister;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gongwenjie09147
 */
public class MyRegister implements SproutsRegister {

    @Override
    public void registerBeans(AnnotationMetadata metaData, BeanDefinitionRegistry registry) {
        registerBeanWithArgs(registry, String.class, "aaa");
    }
}

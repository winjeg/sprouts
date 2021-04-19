package com.winjeg.sprouts.common.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gongwenjie09147
 */
public interface SproutsRegister {

    /**
     * register beans to spring context
     *
     * @param metaData the annotation metadata to be processed
     * @param registry the bean defination of spring
     */
    void registerBeans(AnnotationMetadata metaData, BeanDefinitionRegistry registry);
}

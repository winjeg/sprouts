package com.winjeg.sprouts.spring;

import com.winjeg.sprouts.common.spring.CommonRegister;
import com.winjeg.sprouts.common.spring.SproutsRegister;
import com.winjeg.sprouts.spring.util.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * @author gongwenjie09147
 */
@Slf4j
public class SproutsBoot implements ImportBeanDefinitionRegistrar, CommonRegister, ResourceLoaderAware {

    /**
     * the entry to make all things happen
     *
     * @param metaData annotation metadata
     * @param registry bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metaData, @NonNull BeanDefinitionRegistry registry) {
        val classList = ResourceUtil.resolveClassWithInterface(SproutsRegister.class);
        if (classList.size() == 0) {
            return;
        }
        for (val clz : classList) {
            try {
                SproutsRegister register = (SproutsRegister) clz.newInstance();
                register.registerBeans(metaData, registry);
            } catch (Exception e) {
                log.error("registerBeanDefinitions - error registering beans className:{}", clz.getName());
            }
        }
    }

    /**
     * scan all the implementations of BricksRegister
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param resourceLoader the resource loader
     */
    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        ResourceUtil.initResouces(resourceLoader);
    }
}

package com.winjeg.sprouts.spring;

import com.winjeg.sprouts.common.spring.CommonRegister;
import com.winjeg.sprouts.common.spring.SproutsRegister;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongwenjie09147
 */
@Slf4j
public class SproutsBoot implements ImportBeanDefinitionRegistrar, CommonRegister, ResourceLoaderAware {

    private static final ConcurrentHashMap<String, SproutsRegister> INJECTORS = new ConcurrentHashMap<>();

    /**
     * the entry to make all things happen
     *
     * @param metaData annotation metadata
     * @param registry bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metaData, @NonNull BeanDefinitionRegistry registry) {
        if (CollectionUtils.isEmpty(INJECTORS)) {
            return;
        }
        for (val injector : INJECTORS.values()) {
            injector.registerBeans(metaData, registry);
        }
    }

    /**
     * scan all the implementations of BricksRegister
     *
     * @param resourceLoader the resource loader
     */
    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = new Resource[0];
        try {
            resources = resolver.getResources("classpath*:/com/hellobike/**/*.class");
        } catch (IOException e) {
            log.error("setResourceLoader - getResources error happened when scanning config", e);
        }
        for (Resource r : resources) {
            MetadataReader reader = null;
            try {
                reader = metaReader.getMetadataReader(r);
            } catch (IOException e) {
                log.error("setResourceLoader - getMetadataReader error happened when scanning config", e);
                continue;
            }
            reader.getClassMetadata();
            val className = reader.getClassMetadata().getClassName();
            if (className.length() == 0) {
                continue;
            }
            Class<?> clz = null;
            if (className.contains("$")) {
                continue;
            }
            try {
                clz = Class.forName(className);
                Object obj = clz.newInstance();
                if (obj instanceof SproutsRegister) {
                    INJECTORS.put(className, (SproutsRegister) obj);
                }
            } catch (Throwable ignored) {
            }
        }
    }
}

package com.winjeg.sprouts.spring.util;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author gongwenjie09147
 */
@Slf4j
public class ResourceUtil {
    private static final int CLZ_NUM = 16;
    private static final String ALL_PATH = "classpath*:/**/*.class";
    private static final AtomicBoolean HAS_SET = new AtomicBoolean(false);
    private static final String[] SYSTEM_PATH = new String[]{"sun.", "java.", "javax.", "javafx.", "jdk.", "oracle.", "com.sun.",
            "com.oracle.", "netscape.", "com.winjeg.sprouts.common.spring.SproutsRegister"};
    private static final List<String> ALL_CLASSES = new ArrayList<>();

    public static Class<?> findClassWithAnnotation(Class<?> annoClz) {
        for (String clzName : ALL_CLASSES) {
            val clz = createClassByName(clzName);
            if (clz == null) {
                continue;
            }
            val annos = clz.getAnnotations();
            for (val an : annos) {
                if (annoClz.isInstance(an)) {
                    return clz;
                }
            }
        }
        return null;
    }

    public static <T> T findAnnotation(Class<T> annoClz) {
        for (String clzName : ALL_CLASSES) {
            val clz = createClassByName(clzName);
            if (clz == null) {
                continue;
            }
            val annos = clz.getAnnotations();
            for (val an : annos) {
                if (annoClz.isInstance(an)) {
                    return (T) an;
                }
            }
        }
        return null;
    }

    /**
     * reslove classes that implements an
     *
     * @param iface
     * @return
     */
    public static List<Class<?>> resolveClassWithInterface(Class<?> iface) {
        List<Class<?>> classes = new ArrayList<>();
        for (String clzName : ALL_CLASSES) {
            val clz = createClassByName(clzName);
            if (clz == null) {
                continue;
            }
            if (iface.isAssignableFrom(clz)) {
                classes.add(clz);
            }
        }
        return classes;
    }

    /**
     * set resource loader and scan classes.
     * this method should only be called once.
     *
     * @param rl the resource loader to get classes
     */
    public static void initResouces(ResourceLoader rl) {
        if (HAS_SET.get()) {
            return;
        }
        if (rl != null) {
            synchronized (ResourceUtil.class) {
                long start = System.currentTimeMillis();
                val metaReader = new CachingMetadataReaderFactory(rl);
                ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(rl);
                Resource[] res;
                try {
                    res = resolver.getResources("classpath*:/**/*.class");
                    for (val r : res) {
                        val rd = metaReader.getMetadataReader(r);
                        rd.getClassMetadata();
                        val className = rd.getClassMetadata().getClassName();
                        if (className.length() == 0 || className.contains("$") || isSystemClass(className)) {
                            continue;
                        }
                        ALL_CLASSES.add(className);
                    }
                } catch (IOException e) {
                    log.error("getResouces - getResources error happened when scanning config", e);
                }
                log.info("setResourceLoader - class scan cost: {}ms", System.currentTimeMillis() - start);
            }
        }
        HAS_SET.compareAndSet(false, true);
    }

    /**
     * to judge if a class is a system class or not
     *
     * @param path class name full path
     * @return is jdk class or not
     */
    private static boolean isSystemClass(String path) {
        for (val p : SYSTEM_PATH) {
            if (path.startsWith(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * create class via class.forname
     *
     * @param className name of the class
     * @return the classes created
     */
    private static Class<?> createClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (Throwable ignored) {
            return null;
        }
    }
}

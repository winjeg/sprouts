package com.winjeg.sprouts.spring.anno;

import com.winjeg.sprouts.spring.SproutsBoot;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author gongwenjie09147
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(SproutsBoot.class)
public @interface UseSprouts {
    String scanPath() default "";
}

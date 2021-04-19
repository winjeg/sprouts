package com.winjeg.sprouts.common.helper;

/**
 * @author gongwenjie09147
 */
public @interface SproutsOrder {

    /**
     * the order is to make sure the framework will create beans in actual order
     *
     * @return the value of the order
     */
    int value() default Integer.MAX_VALUE;
}

package com.winjeg.sprouts.common.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gongwenjie09147
 */

@AllArgsConstructor
@Getter
public enum CommonOrder {

    /**
     * before the creation of most beans
     */
    BEFORE_MOST_BEANS(9999, "before most beans"),

    /**
     * care for
     */
    ORDER_NORMAL(100000, "normal"),

    /**
     * after spring started the beans will be injected
     */
    AFTER_SPRING_STARTED(999999, "after_spring_started");

    /**
     * the value of the order
     */
    private final int value;

    /**
     * the name of the order
     */
    private final String name;
}

package com.winjeg.sprouts.spring.util;

import lombok.val;

/**
 * @author gongwenjie09147
 */
public class StringUtil {
    public static boolean allEmpty(String... args) {
        for (val arg : args) {
            if (arg != null && arg.trim().length() > 0) {
                return false;
            }
        }
        return true;
    }
}

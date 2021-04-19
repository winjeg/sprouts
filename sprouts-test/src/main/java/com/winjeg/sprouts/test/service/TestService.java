package com.winjeg.sprouts.test.service;

import org.springframework.stereotype.Service;

/**
 * @author gongwenjie09147
 */
@Service
public class TestService {

    private static final String PONG = "pong";

    public String pong() {
        return PONG;
    }
}

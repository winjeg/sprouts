package com.winjeg.sprouts.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author gongwenjie09147
 */
@Service
public class TestService {


    @Autowired
    @Qualifier("hello")
    private String name;

    public String pong() {
        return name;
    }
}

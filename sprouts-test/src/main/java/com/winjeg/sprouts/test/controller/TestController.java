package com.winjeg.sprouts.test.controller;

import com.winjeg.sprouts.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gongwenjie09147
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private String name;

    @GetMapping("/ping")
    public String pong() {
        return testService.pong();
    }
}

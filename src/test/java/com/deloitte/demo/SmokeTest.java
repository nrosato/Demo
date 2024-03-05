package com.deloitte.demo;

import com.deloitte.demo.controllers.SimpleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private SimpleController simpleController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(simpleController).isNotNull();
    }

}

package com.learning.springboot.redis;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message){
        LOGGER.info("Received <"+message+">");
        latch.countDown();
    }
}

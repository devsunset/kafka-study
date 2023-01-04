package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerWorkerMulti implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ConsumerWorkerMulti.class);
    private String recordValue;

    ConsumerWorkerMulti(String recordValue) {
        this.recordValue = recordValue;
    }

    @Override
    public void run() {
        logger.info("thread:{}\trecord:{}", Thread.currentThread().getName(), recordValue);
    }
}
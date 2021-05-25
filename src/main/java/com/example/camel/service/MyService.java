package com.example.camel.service;

import com.newrelic.api.agent.Trace;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    

    @Trace
    public void hello(){
        System.out.println("Inside Service");
    }
}

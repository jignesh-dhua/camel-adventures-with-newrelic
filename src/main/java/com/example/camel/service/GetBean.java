package com.example.camel.service;

import com.example.camel.domain.ResponseType;

import org.springframework.stereotype.Component;

@Component
public class GetBean {

    public ResponseType sayHello() {
        // Your logic can go here! If you return a POJO, Camel will try and serialise it to JSON.
        return new ResponseType("Hello, world!");
    }

}
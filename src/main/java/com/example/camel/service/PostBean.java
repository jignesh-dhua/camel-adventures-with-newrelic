package com.example.camel.service;

import com.example.camel.domain.RequestType;
import com.example.camel.domain.ResponseType;

import org.springframework.stereotype.Component;

@Component
public class PostBean {

    public ResponseType response(RequestType input) {
        // We create a new object of the ResponseType
        // Camel will be able to serialise this automatically to JSON
        return new ResponseType("Thanks for your post, " + input.getName() + "!");
    }
}
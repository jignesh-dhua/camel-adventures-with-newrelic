package com.example.camel.route;

import com.newrelic.api.agent.Trace;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class TheProcessor implements Processor {

    @Trace
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Inside Processor");
        exchange.getIn().setBody(null);
        
    }
    
}

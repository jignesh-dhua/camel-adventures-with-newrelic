package com.example.camel.config;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.zipkin.ZipkinTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.urlconnection.URLConnectionSender;

@Component
public class CustomCamelContextConfiguration implements CamelContextConfiguration {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void beforeApplicationStart(CamelContext context) {

		log.info("################### Before Application Start ############################");

		context.setAllowUseOriginalMessage(true);

		context.setShutdownStrategy(new CustomShutdownStrategy());

		context.getShutdownStrategy().setLogInflightExchangesOnTimeout(true);
		context.getShutdownStrategy().setTimeUnit(TimeUnit.SECONDS);
		context.getShutdownStrategy().setTimeout(30);

		
		
		
		 // create zipkin
        ZipkinTracer zipkin = new ZipkinTracer();
        zipkin.setEndpoint("https://trace-api.eu.newrelic.com/trace/v1?Api-Key=a47a1b93a42290619a8f5968ca97df003cd6NRAL&Data-Format=zipkin&Data-Format-Version=2");
        Reporter<zipkin2.Span> spanReporter = AsyncReporter.create(URLConnectionSender.create("https://trace-api.eu.newrelic.com/trace/v1?Api-Key=a47a1b93a42290619a8f5968ca97df003cd6NRAL&Data-Format=zipkin&Data-Format-Version=2"));
		zipkin.setSpanReporter(spanReporter);
      
        // capture 100% of all the events
        zipkin.setRate(1.0f);
        // include message bodies in the traces (not recommended for production)
       zipkin.setIncludeMessageBodyStreams(true);
        

        // register zipkin to CamelContext
        zipkin.init(context);
        
		
//		ZipkinTracer tracer = new ZipkinTracer();
//		
		
//		
//		tracer.setRate(1.0f);
//		tracer.init(camelContext);
		
	}

	@Override
	public void afterApplicationStart(CamelContext camelContext) {
		log.info("################### After Application Start  ############################");
	}
}

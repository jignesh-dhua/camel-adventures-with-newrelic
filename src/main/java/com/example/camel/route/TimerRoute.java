package com.example.camel.route;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.camel.config.AbstractRouteBuilder;
import com.example.camel.config.ShutdownManager;

@Component
public class TimerRoute extends AbstractRouteBuilder {

	@Autowired
	ShutdownManager shutdownManager;
	
	@Override
	public void configure() throws Exception {

		onException(IllegalAccessException.class)
			.log("*** onException() *** ");

		from("timer://foo?repeatCount=1")
			.routeId(getClass().getName())
			.setBody(constant("Hello World"))
			.log("*** Timer started ***")
			.delay(5000)
		
			//.throwException(new IllegalAccessException("Something is wrong"))		
		
			.to("file://Test")
			.log("*** Done ***");
		
	}

	@Override
	public void onSuccess(Exchange exchange) {
		log.info("*** onSuccess() ***");
		//shutdownManager.initiateShutdown(0);
	}

	@Override
	public void onFailure(Exchange exchange) {
		log.info("*** onFailure() ***");
		//shutdownManager.initiateShutdown(1);
	}
}
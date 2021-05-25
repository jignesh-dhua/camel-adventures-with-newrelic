package com.example.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.camel.config.AbstractRouteBuilder;
import com.example.camel.config.ShutdownManager;
import com.example.camel.service.MyService;

@Component
public class TimerRoute extends AbstractRouteBuilder {

	@Autowired
	ShutdownManager shutdownManager;

	@Autowired
    TheProcessor theProcessor;

	@Autowired
	MyService myService;
	
	@Override
	public void configure() throws Exception {

		onException(IllegalAccessException.class)
			.log("*** onException() *** ");

			from("timer:trigger?repeatCount=1")
			.streamCaching()
			.routeId(getClass().getName())
				.to("bean:myService")
				.log(LoggingLevel.INFO, log, "Before tx: ${body}")
				.to(TransformerRoute.FROM_ENDPOINT)
				.log(LoggingLevel.INFO, log, "After tx: ${body}")
				.log("Calling webservices")
				.process(theProcessor)
				.toD("https://api.github.com/users/jignesh-dhua")
				.log("Response: ${body}")
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
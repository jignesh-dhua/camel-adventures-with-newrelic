package com.example.camel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransformerRoute extends RouteBuilder {

	public static final String FROM_ENDPOINT = "direct:transform";
	
	@Override
	public void configure() throws Exception {

		 from(FROM_ENDPOINT)
		 	.routeId(getClass().getName())
		 	.transform(simple("Transformed-${body}"))
	        .log(LoggingLevel.INFO, log, "${body}");
	}
}

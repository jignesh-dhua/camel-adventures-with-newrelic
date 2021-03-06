package com.example.camel.config;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.support.RoutePolicySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomRoutePolicy extends RoutePolicySupport implements CamelContextAware {

	protected Logger log = LoggerFactory.getLogger(getClass());

	private CamelContext camelContext;


	@Override
	public void onExchangeBegin(Route route, Exchange exchange) {
		log.info("STARTED PROCESSING MESSAGES/FILES");
		super.onExchangeBegin(route, exchange);
	}

	@Override
	public void onExchangeDone(Route route, Exchange exchange) {
		Object exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
		if (exception != null || exchange.getException() != null) {
			log.error("FINISHED PROCESSING MESSAGES/FILES: FAILURE");

		} else {
			log.info("FINISHED PROCESSING MESSAGES/FILES: SUCCESS");
		}

		super.onExchangeDone(route, exchange);
	}
	@Override
	public void onStart(Route route) {
		log.info("#### Route onStart() ####");
		super.onStart(route);
	}
	@Override
	public void shutdown() throws Exception {
		log.info("#### Route shutdown() ####");
		super.shutdown();
	}
	
	@Override
	public void onStop(Route route) {
		log.info("#### Route onStop() ####");
		super.onStop(route);
	}

	@Override
	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}

	@Override
	public CamelContext getCamelContext() {

		return camelContext;
	}
}
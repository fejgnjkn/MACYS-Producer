package com.zensar.services;

import org.springframework.web.bind.annotation.RequestBody;

import com.zensar.dto.FulfillmentOrder;
import com.zensar.dto.OrderMessage;

public interface OrderMessageProducerService {
	
	String publishJsonMessage(OrderMessage orderMessage);
	String publishXmlMessages(FulfillmentOrder fulfillmentOrder);

}

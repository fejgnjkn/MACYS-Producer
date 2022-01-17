package com.zensar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.dto.FulfillmentOrder;
import com.zensar.dto.OrderMessage;
import com.zensar.services.OrderMessageProducerService;

@RestController
public class OrderMessageProducerController {

	@Autowired
	OrderMessageProducerService orderMessageProducerService;

	@PostMapping(value = "/publish/xml", consumes = {
			MediaType.APPLICATION_XML_VALUE })
	public String publishXmlMessages(@RequestBody FulfillmentOrder fulfillmentOrder) {
		return orderMessageProducerService.publishXmlMessages(fulfillmentOrder);
	}

	@PostMapping(value = "/publish/json", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public String publishJsonMessage(@RequestBody OrderMessage orderMessage) {
		return orderMessageProducerService.publishJsonMessage(orderMessage);

	}

}

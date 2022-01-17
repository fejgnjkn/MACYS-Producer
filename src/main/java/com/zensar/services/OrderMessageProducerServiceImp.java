package com.zensar.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.zensar.config.OrderProducerConfig;
import com.zensar.dto.FulfillmentOrder;
import com.zensar.dto.OrderMessage;

@Service
public class OrderMessageProducerServiceImp implements OrderMessageProducerService {

	

	@Autowired
	public RabbitTemplate jsonRabbitTemplate;
	
	@Autowired
	public RabbitTemplate xmlRabbitTemplate;
	
	
	@Override
	public String publishJsonMessage(OrderMessage orderMessage) {
		jsonRabbitTemplate.convertAndSend(OrderProducerConfig.QueueType.JSON_QUEUE,orderMessage);
		return "Json Message is published by producer";
	}


	@Override
	public String publishXmlMessages(FulfillmentOrder fulfillmentOrder) {
		xmlRabbitTemplate.convertAndSend(OrderProducerConfig.QueueType.XML_QUEUE,
				fulfillmentOrder);
		return "xml Message is published by producer";
	}

	
}


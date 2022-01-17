package com.zensar.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderProducerConfig {

	public static final String ROUTING_KEY = "message_routingkey";
	public static final String MESSAGE_EXCHANGE = "message_exchange";

	public static class QueueType {
		public static final String XML_QUEUE = "XML_QUEUE";
		public static final String JSON_QUEUE = "JSON_QUEUE";

	}


	@Bean
	public Queue xmlQueue() {
		return new Queue(QueueType.XML_QUEUE);
	}

	@Bean
	public Queue jsonQueue() {
		return new Queue(QueueType.JSON_QUEUE);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(MESSAGE_EXCHANGE);
	}


	@Bean
	Binding xmlBinding(TopicExchange exchange) {
		return BindingBuilder.bind(xmlQueue()).to(exchange).with(xmlQueue().getName());
	}

	@Bean
	Binding jsonBinding(TopicExchange exchange) {
		return BindingBuilder.bind(jsonQueue()).to(exchange).with(jsonQueue().getName());
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public MessageConverter xmlMessageConverter() {
		return new Jackson2XmlMessageConverter();
	}

	@Bean
	public AmqpTemplate xmlTemplate(ConnectionFactory connectionFactory) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setRoutingKey(ROUTING_KEY);
		rabbitTemplate.setMessageConverter(xmlMessageConverter());
		rabbitTemplate.setDefaultReceiveQueue(xmlQueue().getName());
		return rabbitTemplate;
	}
	
	@Bean
	public AmqpTemplate jsonTemplate(ConnectionFactory connectionFactory) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setRoutingKey(ROUTING_KEY);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		rabbitTemplate.setDefaultReceiveQueue(jsonQueue().getName());
		return rabbitTemplate;
	}
}

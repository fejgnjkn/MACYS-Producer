package com.zensar.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableRabbit
public class OrderProducerConfig {

	public static final String ROUTING_KEY_XML = "routing_key_xml";
	public static final String ROUTING_KEY_JSON = "routing_key_json";
	public static final String MESSAGE_EXCHANGE = "message_exchange";

	public static class QueueType {
		public static final String XML_QUEUE = "XML_QUEUE";
		public static final String JSON_QUEUE = "JSON_QUEUE";

	}
	

	@Autowired
	ConnectionFactory connectionFactory;
	

	@Bean
	public Queue queueXml() {

		return new Queue(QueueType.XML_QUEUE, true);
	}

	@Bean
	public Queue queueJson() {

		return new Queue(QueueType.JSON_QUEUE, true);
	}

	@Bean
	public TopicExchange exchange() {

		return new TopicExchange(MESSAGE_EXCHANGE);
	}

	@Bean
	public Binding bindingJson(TopicExchange exchange) {

		return BindingBuilder.bind(queueJson()).to(exchange).with(ROUTING_KEY_JSON);
	}

	@Bean
	public Binding bindingXml(TopicExchange exchange) {

		return BindingBuilder.bind(queueXml()).to(exchange).with(ROUTING_KEY_XML);
	}

	@Bean
	public MessageConverter messageConverterJson() {
//		/*
//		 * 
//		 * mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
//		 */
		ObjectMapper mapper = new ObjectMapper();
		return new Jackson2JsonMessageConverter(mapper);
	}

	@Bean
	public MessageConverter messageConverterXml() {

		return new Jackson2XmlMessageConverter();
	}
	
	@Bean
	public AmqpTemplate xmlRabbitTemplate(ConnectionFactory connectionFactory) {

		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterXml());
		rabbitTemplate.setRoutingKey(ROUTING_KEY_XML);
		rabbitTemplate.setDefaultReceiveQueue(QueueType.XML_QUEUE);
		return rabbitTemplate;
	}

	
	@Bean
	public AmqpTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {

		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setRoutingKey(ROUTING_KEY_JSON);
//		rabbitTemplate.setDefaultReceiveQueue(QueueType.JSON_QUEUE);
		rabbitTemplate.setMessageConverter(messageConverterJson());

		return rabbitTemplate;
	}

}

package com.zensar.controller;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zensar.MacysOrderMessageProducerApplication;
import com.zensar.dto.FulfillmentOrder;
import com.zensar.services.OrderMessageProducerService;

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = MacysOrderMessageProducerApplication.class)
@AutoConfigureMockMvc
public class OrderMessageProducerControllerTest {
	

	@Mock
	OrderMessageProducerService orderMessageProducerService;

	@Autowired
	MockMvc mockMvc;
	
	FulfillmentOrder fulfillmentOrder;
	
	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		fulfillmentOrder = new FulfillmentOrder();
	}
	
	
	@Test
	final void testPublishJsonMessages() throws Exception {
		String jsonString= "{\"command\":\"ADD\",\"imagePathname\":null,\"itemDescription\":\"FLAT WALLET\",\"itemHeight\":1.3,\"itemLength\":2.3,\"itemName\":\"WALLET\",\"itemWeight\":1.5,\"itemWidth\":0.5,\"messageName\":\"TannMessage\",\"pickType\":\"E\",\"rfidTagged\":\"Y\",\"storageAttribute\":1,\"upcList\":null}";
		mockMvc.perform(MockMvcRequestBuilders.post("/publish/json").content(jsonString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	final void testPublishXmlMessages() throws Exception {
		
		when(orderMessageProducerService.publishXmlMessages(fulfillmentOrder)).thenReturn("xmml Message is published by producer");
//		mockMvc.perform(MockMvcRequestBuilders.post("/publish/xml").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk()).andReturn();
	}
	

}

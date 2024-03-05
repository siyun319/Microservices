package com.siyun.productservice;

import org.springframework.http.MediaType;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.siyun.productservice.dto.ProductRequest;
import com.siyun.productservice.repository.ProductRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	// the mongo version we want to use for this test
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");



	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	// concert POJO to JSON and vice versa;
	@Autowired
	private ObjectMapper objectMapper;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		// we are not going to use the local real database,
		// we provide the uri dynamically
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	//integration test
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		// mock mvc -> mock servelet
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(productRequestString))
				.andExpect(status().isCreated());

		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	@Test
	void shouldGetProduct() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
				.andExpect(status().isOk());

	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
							.name("iphone 13")
							.description("iphone 13")
							.price(BigDecimal.valueOf(1200))
							.build();
	}



}

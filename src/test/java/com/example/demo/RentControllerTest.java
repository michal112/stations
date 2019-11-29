package com.example.demo;

import com.example.demo.data.BikeFactory;
import com.example.demo.data.RentFactory;
import com.example.demo.model.mapper.BikeMapper;
import com.example.demo.model.mapper.RentMapper;
import com.example.demo.model.payload.BikePayload;
import com.example.demo.model.payload.RentPayload;
import com.example.demo.model.repository.BikeRepository;
import com.example.demo.model.repository.RentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RentRepository rentRepository;

	@Autowired
	private BikeRepository bikeRepository;

	@Autowired
	private RentMapper rentMapper;

	@Autowired
	private BikeMapper bikeMapper;

	@Autowired
	private ObjectMapper objectMapper;

	private RentPayload rentPayload;

	private BikePayload bikePayload;

	@Before
	public void init() {
		bikePayload = BikeFactory.createSimpleBike();
		rentPayload = RentFactory.createSimpleRent(bikePayload.getPublicId());
	}

	@Test
	public void shouldCreateEntity() throws Exception {
		//given
		var bike = bikeRepository.save(bikeMapper.mapToEntity(bikePayload));
		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/rent/borrow")
				.content(objectMapper.writeValueAsString(rentPayload))
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		//then
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(objectMapper.writeValueAsString(rentPayload), result);
		var rent = rentRepository.getByPublicId(rentPayload.getPublicId());
		Assert.assertEquals(objectMapper.writeValueAsString(rent.getBikeEntity()), objectMapper.writeValueAsString(bike));
	}

	@Test
	public void shouldDeleteEntity() throws Exception {
		//given
		bikeRepository.save(bikeMapper.mapToEntity(bikePayload));
		rentRepository.save(rentMapper.mapToEntity(rentPayload));
		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/rent/return/" + rentPayload.getPublicId()))
				.andReturn();
		//then
		var status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(HttpStatus.OK.value(), status);
		Assert.assertEquals(0, rentRepository.count());
	}
}

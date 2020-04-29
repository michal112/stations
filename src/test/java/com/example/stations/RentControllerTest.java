package com.example.stations;

import com.example.stations.data.BikeFactory;
import com.example.stations.data.RentFactory;
import com.example.stations.model.mapper.BikeMapper;
import com.example.stations.model.mapper.RentMapper;
import com.example.stations.model.payload.BikePayload;
import com.example.stations.model.payload.RentPayload;
import com.example.stations.model.repository.BikeRepository;
import com.example.stations.model.repository.RentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
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

	@BeforeEach
	public void init() {
		bikePayload = BikeFactory.createSimpleBike();
		rentPayload = RentFactory.createSimpleRent(bikePayload.getPublicId());
	}

	@AfterEach
	public void after() {
		rentRepository.deleteAll();
		bikeRepository.deleteAll();
	}

	@DisplayName("Should borrow bike and create rent entity")
	@Test
	public void shouldBorrowBike() throws Exception {
		//given
		var bike = bikeRepository.save(bikeMapper.mapToEntity(bikePayload));
		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/rent/borrow")
				.content(objectMapper.writeValueAsString(rentPayload))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		//then
		var response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RentPayload.class);
		Assert.assertEquals(rentPayload.getBikePublicId(), response.getBikePublicId());
		var rent = rentRepository.getByPublicId(response.getPublicId());
		Assert.assertEquals(objectMapper.writeValueAsString(rent.getBike()), objectMapper.writeValueAsString(bike));
	}

	@DisplayName("Should return bike and delete rent entity")
	@Test
	public void shouldReturnBike() throws Exception {
		//given
		var bike = bikeRepository.save(bikeMapper.mapToEntity(bikePayload));
		var rent = rentRepository.save(rentMapper.mapToEntity(rentPayload, bike));
		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/rent/return/" + rent.getPublicId()))
				.andReturn();
		//then
		var status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(HttpStatus.OK.value(), status);
		Assert.assertEquals(0, rentRepository.count());
	}
}

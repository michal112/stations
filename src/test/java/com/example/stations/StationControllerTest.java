package com.example.stations;

import com.example.stations.data.RentFactory;
import com.example.stations.data.StationFactory;
import com.example.stations.model.entity.BikeEntity;
import com.example.stations.model.entity.StandEntity;
import com.example.stations.model.mapper.RentMapper;
import com.example.stations.model.mapper.StationMapper;
import com.example.stations.model.payload.*;
import com.example.stations.model.repository.BikeRepository;
import com.example.stations.model.repository.RentRepository;
import com.example.stations.model.repository.StandRepository;
import com.example.stations.model.repository.StationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StationRepository stationRepository;

    @Autowired
    private StandRepository standRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private RentRepository rentRepository;

	@Autowired
	private StationMapper stationMapper;

	@Autowired
    private RentMapper rentMapper;

	@Autowired
	private ObjectMapper objectMapper;

	private StationPayload stationPayload;

	@BeforeEach
	public void init() {
		stationPayload = StationFactory.createSimpleStation();
	}

	@AfterEach
    public void after() {
	    stationRepository.deleteAll();
	    rentRepository.deleteAll();
	    bikeRepository.deleteAll();
    }

    @DisplayName("Should create station, stands and bikes associated with this station")
	@Test
	public void shouldCreateStation() throws Exception {
		//given
		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/station")
				.content(objectMapper.writeValueAsString(stationPayload))
				.contentType(MediaType.APPLICATION_JSON))
                .andReturn();
		//then
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(stationPayload, objectMapper.readValue(result, StationPayload.class));

		var station = stationRepository.getByPublicId(stationPayload.getPublicId());

        Assert.assertEquals(station.getName(), stationPayload.getName());
        Assert.assertEquals(station.getNumber(), stationPayload.getNumber());
        Assert.assertTrue(station.getStands().stream()
                .allMatch(stand -> containStand(stationPayload, stand)));
	}

    @DisplayName("Should update station, stands and bikes associated with this station")
    @Test
    public void shouldUpdateStation() throws Exception {
        //given
        stationRepository.save(stationMapper.mapToEntity(stationPayload));
        var updatedPayload = StationFactory.createSimpleStation();
        updatedPayload.setPublicId(stationPayload.getPublicId());
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/station/" + updatedPayload.getPublicId())
                .content(objectMapper.writeValueAsString(updatedPayload))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), status);

        var station = stationRepository.getByPublicId(updatedPayload.getPublicId());

        Assert.assertEquals(station.getName(), updatedPayload.getName());
        Assert.assertEquals(station.getNumber(), updatedPayload.getNumber());
        Assert.assertTrue(station.getStands().stream()
                .allMatch(stand -> containStand(updatedPayload, stand)));
    }

    @DisplayName("Should delete station, stands and bikes associated with this station")
    @Test
    public void shouldDeleteStation() throws Exception {
        //given
        stationRepository.save(stationMapper.mapToEntity(stationPayload));
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/station/" + stationPayload.getPublicId()))
                .andReturn();
        //then
        var status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);

        Assert.assertEquals(0, stationRepository.count());
        Assert.assertEquals(0, standRepository.count());
        Assert.assertEquals(0, bikeRepository.count());
    }

    @DisplayName("Should return stations info with correct stands and bikes")
    @Test
    public void shouldReturnStationsInfo() throws Exception {
        //given
        var station = stationRepository.save(stationMapper.mapToEntity(stationPayload));
        var bike = station.getStands().stream()
                .flatMap(stand -> stand.getBikes().stream())
                .findAny()
                .get();
        rentRepository.save(rentMapper.mapToEntity(RentFactory.createSimpleRent(bike.getPublicId()), bike));
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/station/info"))
                .andReturn();
        //then
        String result = mvcResult.getResponse().getContentAsString();
        List<StationInfoPayload> list = objectMapper.readValue(result, new TypeReference<>(){});

        Assert.assertEquals(1, list.size());
        StationInfoPayload payload = list.get(0);

        Assert.assertEquals(stationPayload.getName(), payload.getName());
        Assert.assertEquals(stationPayload.getNumber(), payload.getNumber());
        Assert.assertEquals(stationPayload.getStands().stream()
                .filter(StandPayload::getAvailable)
                .count(), payload.getStandsAvailable().longValue());
        Assert.assertEquals(stationPayload.getStands().stream()
                .filter(stand -> !stand.getAvailable())
                .count(), payload.getStandsUnavailable().longValue());
        Assert.assertEquals(stationPayload.getStands().stream()
                .mapToLong(standPayload -> standPayload.getBikes().size())
                .sum() - 1, payload.getBikesAvailable().longValue());
	}

    @DisplayName("Should return stations with correct stands and bikes")
    @Test
    public void shouldReturnStations() throws Exception {
        //given
        var station = stationRepository.save(stationMapper.mapToEntity(stationPayload));
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/station"))
                .andReturn();
        //then
        String result = mvcResult.getResponse().getContentAsString();
        List<StationPayload> list = objectMapper.readValue(result, new TypeReference<>(){});

        Assert.assertEquals(1, list.size());
        StationPayload payload = list.get(0);

        Assert.assertEquals(station.getName(), payload.getName());
        Assert.assertEquals(station.getNumber(), payload.getNumber());
        Assert.assertEquals(station.getPublicId(), payload.getPublicId());
        Assert.assertTrue(station.getStands().stream()
                .allMatch(stand -> containStand(payload, stand)));
    }

    private boolean containStand(StationPayload stationPayload, StandEntity stand) {
        return stationPayload.getStands().stream()
                .anyMatch(standPayload -> equal(standPayload, stand));
    }

    private boolean equal(StandPayload standPayload, StandEntity stand) {
        return stand.getNumber().equals(standPayload.getNumber()) &&
                stand.getAvailable().equals(standPayload.getAvailable()) &&
                stand.getPublicId().equals(standPayload.getPublicId()) &&
                stand.getBikes().stream()
                    .allMatch(bike -> containBike(standPayload, bike));
    }

    private boolean containBike(StandPayload standPayload, BikeEntity bike) {
        return standPayload.getBikes().stream()
                .anyMatch(bikePayload -> equal(bikePayload, bike));
    }

    private boolean equal(BikePayload bikePayload, BikeEntity bike) {
        return bike.getNumber().equals(bikePayload.getNumber()) &&
                bike.getPublicId().equals(bikePayload.getPublicId());
    }
}

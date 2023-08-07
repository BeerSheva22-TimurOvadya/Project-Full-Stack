package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.controller.AdvertController;
import telran.spring.model.Advert;

import java.util.Arrays;
import java.util.List;

@WebMvcTest({ AdvertController.class })
@Import(AdvertControllerTestConfiguration.class)
class AdvertControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	Advert advert;
	String advertsUrl = "http://localhost:8080/adverts";
	String categoryUrl = String.format("%s/category/test", advertsUrl);
	String priceUrl = String.format("%s/price?maxPrice=200", advertsUrl);

	@BeforeEach
	void setUp() {
		advert = new Advert(AdvertControllerTestConfiguration.TEST_ID, "test", "test", 100, null);
	}

	@Test
	void mockMvcExists() {
		assertNotNull(mockMvc);
	}

	@Test
	void addAdvertTest() throws Exception {
		String advertJson = mapper.writeValueAsString(advert);
		mockMvc.perform(post(advertsUrl).contentType(MediaType.APPLICATION_JSON).content(advertJson)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getAllAdvertsTest() throws Exception {
		mockMvc.perform(get(advertsUrl)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void getAdvertsByCategoryTest() throws Exception {
		mockMvc.perform(get(categoryUrl)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void getAdvertsByPriceTest() throws Exception {
		mockMvc.perform(get(priceUrl)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void editAdvertTest() throws Exception {
		String advertJson = mapper.writeValueAsString(advert);
		mockMvc.perform(put(advertsUrl + "/" + AdvertControllerTestConfiguration.TEST_ID)
				.contentType(MediaType.APPLICATION_JSON).content(advertJson)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void deleteAdvertTest() throws Exception {
		mockMvc.perform(delete(advertsUrl + "/" + AdvertControllerTestConfiguration.TEST_ID)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void addAdvertsTest() throws Exception {
		List<Advert> adverts = Arrays.asList(advert, advert);
		String advertsJson = mapper.writeValueAsString(adverts);
		mockMvc.perform(post(advertsUrl + "/batch").contentType(MediaType.APPLICATION_JSON).content(advertsJson))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void addAdvertWithMissingFieldsTest() throws Exception {
		advert.setName(null);
		String advertJson = mapper.writeValueAsString(advert);
		mockMvc.perform(post(advertsUrl).contentType(MediaType.APPLICATION_JSON).content(advertJson)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void getAdvertsByInvalidPriceTest() throws Exception {
		String invalidPriceUrl = String.format("%s/price?maxPrice=-200", advertsUrl);
		mockMvc.perform(get(invalidPriceUrl)).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void editNonExistingAdvertTest() throws Exception {
		String advertJson = mapper.writeValueAsString(advert);
		mockMvc.perform(put(advertsUrl + "/" + 1).contentType(MediaType.APPLICATION_JSON).content(advertJson))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void deleteNonExistingAdvertTest() throws Exception {
		mockMvc.perform(delete(advertsUrl + "/" + 1)).andDo(print()).andExpect(status().isBadRequest());
	}
}

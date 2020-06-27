package com.jeff.gift.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.jsonpath.JsonPath;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class GiftIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void create시_필수_파라메터_없으면_에러() throws Exception {
		mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234"))
			.andExpect(status().isBadRequest());
		
		mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234")
				.param("amount", "10000"))
			.andExpect(status().isBadRequest());
		
		mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234")
				.param("people", "3"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 헤더_정보_없으면_401_에러() throws Exception {
		mvc.perform(post("/gifts").header("X-ROOM-ID", "1234"))
			.andExpect(status().isUnauthorized());
		
		mvc.perform(post("/gifts").header("X-USER-ID", "1234"))
			.andExpect(status().isUnauthorized());
		
		mvc.perform(post("/gifts"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void 토큰_발급() throws Exception {
		mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234")
            .param("amount", "10000")
            .param("people", "3")
            )
			.andExpect(status().isCreated())
			.andDo(print());
	}
	
	@Test
	public void 뿌린것_가지기() throws Exception {
		MvcResult result = mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234")
	            .param("amount", "10000")
	            .param("people", "3")
	            ).andReturn();
		
		String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
		
		mvc.perform(get("/gifts/" + token).header("X-USER-ID", "2345").header("X-ROOM-ID", "ROOM1234"))
			.andExpect(status().isOk());
		mvc.perform(get("/gifts/" + token).header("X-USER-ID", "2346").header("X-ROOM-ID", "ROOM1234"))
		.andExpect(status().isOk());
		mvc.perform(get("/gifts/" + token).header("X-USER-ID", "2347").header("X-ROOM-ID", "ROOM1234"))
		.andExpect(status().isOk());
		mvc.perform(get("/gifts/" + token).header("X-USER-ID", "2348").header("X-ROOM-ID", "ROOM1234"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 뿌리기_조회() throws Exception {
		MvcResult result = mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234")
	            .param("amount", "10000")
	            .param("people", "3")
	            ).andReturn();
		
		String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
		
		mvc.perform(get("/gifts/" + token + "/receipt").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.amount", is(10000)))
			.andExpect(jsonPath("$.givenAmount", is(0)));
		
		mvc.perform(get("/gifts/" + token).header("X-USER-ID", "2345").header("X-ROOM-ID", "ROOM1234"))
			.andExpect(status().isOk());
		
		mvc.perform(get("/gifts/" + token + "/receipt").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.happyPeople", hasSize(1)))
			.andExpect(jsonPath("$.happyPeople[0].keepers", is("2345")));
	}
}

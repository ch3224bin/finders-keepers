package com.jeff.gift.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.jeff.gift.service.GiftService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GiftController.class)
public class GiftControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private TokenModelAssembler tokenModelAssembler;
	
	@MockBean
	private GiftService giftService;
	
	@Test
	public void create시_필수_파라메터_없으면_에러() throws Exception {
		mvc.perform(post("/gifts").header("X-USER-ID", "1234").header("X-ROOM-ID", "ROOM1234"))
				.andExpect(status().isBadRequest())
                .andDo(print());
		
//		mvc.perform(post("/gifts")
//                .param("amount", "0")
//                .param("people", "0")
//                )
//				.andExpect(status().isOk())
//                .andDo(print());
	}
}

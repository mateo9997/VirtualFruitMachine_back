package com.MateoRamon.virtualFruitMachine;

import com.MateoRamon.virtualFruitMachine.controller.GameController;
import com.MateoRamon.virtualFruitMachine.model.Player;
import com.MateoRamon.virtualFruitMachine.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class VirtualFruitMachineApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void playWithSufficientFunds() throws Exception {
		Player player = new Player(10);
		String playerJson = objectMapper.writeValueAsString(player);

		mockMvc.perform(post("http://localhost:3000/api/game/play")
						.contentType(MediaType.APPLICATION_JSON)
						.content(playerJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.remainingMoney").value(9))
				.andExpect(jsonPath("$.slots").isArray())
				.andExpect(jsonPath("$.slots.length()").value(3));
	}

	@Test
	public void playWithInsufficientFunds() throws Exception {
		Player player = new Player(0);
		String playerJson = objectMapper.writeValueAsString(player);

		mockMvc.perform(post("http://localhost:3000/api/game/play")
						.contentType(MediaType.APPLICATION_JSON)
						.content(playerJson))
				.andExpect(status().isBadRequest());
	}
}

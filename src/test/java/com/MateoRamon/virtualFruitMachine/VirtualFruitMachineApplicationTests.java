package com.MateoRamon.virtualFruitMachine;

import static org.hamcrest.Matchers.*;
import com.MateoRamon.virtualFruitMachine.controller.GameController;
import com.MateoRamon.virtualFruitMachine.model.Player;
import com.MateoRamon.virtualFruitMachine.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.Matchers.isOneOf;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class VirtualFruitMachineApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private Set<String> validColors = Set.of("blue", "red", "green", "yellow");
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void apiReturnsCorrectResultObject() throws Exception {
		mockMvc.perform(post("/api/game/play")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"money\": 100}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jackpot", isA(Boolean.class)))
				.andExpect(jsonPath("$.remainingMoney", isA(Integer.class)))
				.andExpect(jsonPath("$.slots", hasSize(3)))
				.andExpect(jsonPath("$.slots[0]", isIn(validColors)))
				.andExpect(jsonPath("$.slots[1]", isIn(validColors)))
				.andExpect(jsonPath("$.slots[2]", isIn(validColors)));
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
	@Test
	public void remainingMoneyDecreasesWhenPlayerLoses() throws Exception {
		int initialMoney = 100;
		Player player = new Player(initialMoney);
		String playerJson = objectMapper.writeValueAsString(player);

		String response = mockMvc.perform(post("/api/game/play")
						.contentType(MediaType.APPLICATION_JSON)
						.content(playerJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		Result result = objectMapper.readValue(response, Result.class);

		assertThat(result.isJackpot()).isFalse();
		assertThat(result.getRemainingMoney()).isEqualTo(initialMoney - 1);
	}



	@Test
	public void remainingMoneyIncreasesWhenPlayerWins() throws Exception {
		GameController gameController = new GameController();
		Player player = new Player(100);
		int initialMoney = player.getMoney();

		while (true) {
			ResponseEntity<Result> responseEntity = gameController.play(player);
			Result result = responseEntity.getBody();

			if (result.isJackpot()) {
				assertThat(result.getRemainingMoney()).isEqualTo(initialMoney + 9);
				break;
			} else {
				initialMoney--;
			}
		}
	}
	@Test
	public void slotsContainValidColors() throws Exception {
		mockMvc.perform(post("/api/game/play")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"money\": 100}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.slots[0]", isOneOf(validColors.toArray())))
				.andExpect(jsonPath("$.slots[1]", isOneOf(validColors.toArray())))
				.andExpect(jsonPath("$.slots[2]", isOneOf(validColors.toArray())));
	}


}

package com.movies.app.movies.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.movies.app.movies.repository.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MoviesControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	MovieRepository movieRepository;

	@Test
	public void getAllMoviesTest() throws Exception {
		mockMvc.perform(get("/movies")).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$[0].title").value("Fast and Furious 6"))
				.andExpect(jsonPath("$[1].title").value("Avatar 2"))
				.andExpect(jsonPath("$[2].title").value("Shawshank Redemption"));
	}
}

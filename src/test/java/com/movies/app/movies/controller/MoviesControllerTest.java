package com.movies.app.movies.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import com.google.gson.Gson;
import com.movies.app.movies.entity.Movie;
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
		mockMvc.perform(get("/movies")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Fast and Furious 6"))
				.andExpect(jsonPath("$[1].title").value("Avatar")).andExpect(jsonPath("$[2].title").value("Shawshank"));
	}

	@Test
	public void getMovieByIdTest() throws Exception {
		mockMvc.perform(get("/movies/{id}", 1)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	@Test
	public void deleteMovieByIdTest() throws Exception {
		mockMvc.perform(delete("/movies/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	public void createMovieTest() throws Exception {
		Movie movie = new Movie(null, "Test", 100, "test.png", "test desc", "testLink");
		Gson gson = new Gson();
		String jsonInString = gson.toJson(movie);
		System.out.println(jsonInString);
		mockMvc.perform(post("/newMovie").contentType(MediaType.APPLICATION_JSON).content(jsonInString))
				.andExpect(status().isOk());
	}

	@Test
	public void testViews() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
		mockMvc.perform(get("/admin")).andExpect(status().isOk()).andExpect(view().name("admin"));
		mockMvc.perform(get("/addMovie")).andExpect(status().isOk()).andExpect(view().name("addMovie"));
		mockMvc.perform(get("/moviesUI")).andExpect(status().isOk()).andExpect(view().name("movies"))
				.andExpect(content().string(containsString("Avatar")));

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		mockMvc.perform(get("/moviesUI/").params(requestParams)).andExpect(status().isOk())
				.andExpect(view().name("moviePage")).andExpect(content().string(containsString("Fast and Furious 6")));
	}

}

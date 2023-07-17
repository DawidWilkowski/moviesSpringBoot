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
import org.springframework.security.test.context.support.WithMockUser;
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

	/**
	 * Get all movies
	 * 
	 * @result GET request is properly performed and status 200 is returned
	 */
	@Test
	public void getAllMoviesTest() throws Exception {
		mockMvc.perform(get("/movies")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Fast and Furious 6"))
				.andExpect(jsonPath("$[1].title").value("Avatar")).andExpect(jsonPath("$[2].title").value("Shawshank"));
	}

	/**
	 * Get movie with id 1
	 * 
	 * @result GET request is properly performed and movie with id 1 is returned
	 */
	@Test
	public void getMovieByIdTest() throws Exception {
		mockMvc.perform(get("/movies/{id}", 1)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	/**
	 * Delete movie with id 1
	 * 
	 * @result DELETE request is properly performed and movie with id 1 is deleted
	 */
	@Test
	public void deleteMovieByIdTest() throws Exception {
		mockMvc.perform(delete("/movies/{id}", 1)).andExpect(status().isOk());
	}

	/**
	 * Creates sample movie
	 * 
	 * @result POST request is properly performed and sample movie is created
	 */
	@Test
	@WithMockUser(roles = "ADMIN")
	public void createMovieTest() throws Exception {
		Movie movie = new Movie(null, "Test", 100, "test.png", "test desc", "testLink");
		Gson gson = new Gson();
		String jsonInString = gson.toJson(movie);
		mockMvc.perform(post("/newMovie").contentType(MediaType.APPLICATION_JSON).content(jsonInString))
				.andExpect(status().isOk());
	}

	/**
	 * Get movie page for movie with id 1
	 * 
	 * @result GET request is properly performed and movie page for movie with id 1
	 *         is returned
	 */
	@Test
	@WithMockUser(roles = "USER")
	public void moviePageTest() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		mockMvc.perform(get("/moviesUI/").params(requestParams)).andExpect(status().isOk())
				.andExpect(view().name("moviePage")).andExpect(content().string(containsString("Fast and Furious 6")));
	}

}

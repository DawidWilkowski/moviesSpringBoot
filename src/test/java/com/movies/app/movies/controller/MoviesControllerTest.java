package com.movies.app.movies.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
				.andExpect(jsonPath("$[1].title").value("Avatar 2"))
				.andExpect(jsonPath("$[2].title").value("Shawshank Redemption"));
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
		Movie movie = new Movie(null, "Test", 100, "test.png");
		Gson gson = new Gson();
		String jsonInString = gson.toJson(movie);
		System.out.println(jsonInString);
		mockMvc.perform(post("/newMovie").contentType(MediaType.APPLICATION_JSON).content(jsonInString))
				.andExpect(status().isOk());
	}

//	@Test
//	public void testListMovies() {
//		List<Movie> movies = (List<Movie>) movieRepository.findAll();
//		assertThat(movies).size().isGreaterThan(3);
//	}
}

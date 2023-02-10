package com.movies.app.movies;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.movies.app.movies.repository.MovieRepository;

@SpringBootTest
class MoviesApplicationTests {
	@Autowired
	MovieRepository movieRepository;

	@Test
	void contextLoads() {
	}

}

package com.movies.app.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.repository.MovieRepository;

@RestController
public class MoviesController {

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping(value = "/movies")
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
}

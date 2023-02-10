package com.movies.app.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(value = "/movies/{id}")
	public ResponseEntity<String> getMovieById(@PathVariable(value = "id") Long id) throws Exception {
		Movie foundMovie = movieRepository.findById(id).orElseThrow(() -> new Exception(" etsT"));
		if (foundMovie != null) {
			return new ResponseEntity<String>(movieRepository.findById(id) + "", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No movie with id. " + id + " found.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/newMovie")
	public ResponseEntity<String> addNewMovie(@RequestParam("title") String title) {
		Movie movie = new Movie();
		movie.setTitle(title);
		movie = movieRepository.save(movie);
		if (movie != null) {
			return new ResponseEntity<String>("Succesfully added new movie", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Movie not created", HttpStatus.NOT_FOUND);
		}
	}

}

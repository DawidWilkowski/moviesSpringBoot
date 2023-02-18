package com.movies.app.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.repository.MovieRepository;

@RestController
public class MoviesController {

	@Autowired
	private MovieRepository movieRepository;

	/**
	 * Returns list of movies from database.
	 */
	@GetMapping(value = "/movies")
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	/**
	 * Returns movie with given id.
	 * 
	 * @param id
	 * 
	 */

	@GetMapping(value = "/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long id) {
		return movieRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Adds new movie to database.
	 * 
	 * @param movie { "id": "", "title": "", "length": }
	 * @return
	 */
	@PostMapping(value = "/newMovie")
	public ResponseEntity<String> addNewMovie(@RequestBody Movie movie) {
		Movie movieFound = movieRepository.findByTitle(movie.getTitle());
		if (movieFound == null) {
			movieRepository.save(movie);
			return new ResponseEntity<String>("Succesfully added new movie", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Movie already exist", HttpStatus.CONFLICT);

		}
	}

}

package com.movies.app.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	 * @param movie id
	 * 
	 */

	@GetMapping(value = "/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long id) {
		return movieRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Delete movie from database.
	 * 
	 * @param movie id
	 */
	@DeleteMapping(value = "/movies/{id}")
	public ResponseEntity<String> deleteMovieById(@PathVariable(value = "id") Long id) {

		if (movieRepository.existsById(id)) {
			movieRepository.deleteById(id);
			return new ResponseEntity<String>("Succesfully deleted movie", HttpStatus.OK);
		}
		return new ResponseEntity<String>("No movie with id: " + id, HttpStatus.CONFLICT);

	}

	/**
	 * Adds new movie to database.
	 * 
	 * @param movie { "id": "", "title": "", "length": }
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

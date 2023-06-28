package com.movies.app.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.service.MoviesService;

@RestController
public class MoviesController {

	@Autowired
	private MoviesService moviesService;

	/**
	 * Returns list of movies from database.
	 */
	@GetMapping(value = "/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		return moviesService.findAll();
	}

	/**
	 * Returns movie with given id.
	 * 
	 * @param movie id
	 * 
	 */

	@GetMapping(value = "/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long id) {
		return moviesService.getMovieById(id);
	}

	/**
	 * Delete movie from database.
	 * 
	 * @param movie id
	 */
	@DeleteMapping(value = "/movies/{id}")
	public ResponseEntity<String> deleteMovieById(@PathVariable(value = "id") Long id) {
		return moviesService.deleteMovieById(id);
	}

	/**
	 * Adds new movie to database.
	 * 
	 * @param movie { "id": "", "title": "", "length": }
	 */

	@PostMapping(value = "/newMovie")
	public ResponseEntity<String> addNewMovie(@RequestBody Movie movie) {
		return moviesService.addNewMovie(movie);
	}

	@PostMapping(value = "/newMovieFromForm")
	public String addNewMovieFromForm(@RequestParam("title") String title, @RequestParam("length") int length,
			@RequestParam("description") String descrpition, @RequestParam("image") MultipartFile imageFile,
			@RequestParam("movie") MultipartFile movieFile) {
		return moviesService.addNewMovieFromForm(title, length, descrpition, imageFile, movieFile);
	}

}

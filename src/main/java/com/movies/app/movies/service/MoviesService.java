package com.movies.app.movies.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.movies.app.movies.entity.Movie;

public interface MoviesService {
	ResponseEntity<List<Movie>> findAll();

	ResponseEntity<Movie> getMovieById(Long id);

	ResponseEntity<String> deleteMovieById(Long id);

	ResponseEntity<String> addNewMovie(Movie movie);

	String addNewMovieFromForm(String title, int length, String description, MultipartFile imageFile,
			MultipartFile movieFile);
}

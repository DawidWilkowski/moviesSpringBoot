package com.movies.app.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.repository.MovieRepository;

@Controller
public class MoviesControllerView {
	@Autowired
	MovieRepository movieRepository;

	/**
	 * Returns main page view.
	 */
	@GetMapping("/index")
	String index(Model model) {
		return "index";
	}

	/**
	 * Returns view for user view.
	 */

	@GetMapping("/moviesUI")
	String moviesUI(Model model) {
		List<Movie> listOfMovies = movieRepository.findAll();
		model.addAttribute("listMovies", listOfMovies);
		return "movies";
	}

	/**
	 * Returns view for chosen movie.
	 * 
	 * @param id
	 */
	@GetMapping("/moviesUI/")
	String viewMovie(Model model, @RequestParam Long id) {
		Movie movie = movieRepository.findById(id).orElseThrow();
		model.addAttribute("movie", movie);
		return "moviePage";
	}

	/**
	 * Returns view for admin page.
	 */
	@GetMapping("/admin")
	String admin(Model model) {
		return "admin";
	}

	/**
	 * Returns view for adding new movie form.
	 */
	@GetMapping("/addMovie")
	String addMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "addMovie";
	}
}

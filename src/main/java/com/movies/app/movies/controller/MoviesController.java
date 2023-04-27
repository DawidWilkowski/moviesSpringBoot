package com.movies.app.movies.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.repository.MovieRepository;

@Controller
public class MoviesController {

	private final String UPLOAD_DIR_IMAGE = "target/classes/static/images/poster/";
	private final String UPLOAD_DIR_MOVIE = "target/classes/static/moviesFolder/";

	@Autowired
	private MovieRepository movieRepository;

	/**
	 * Returns list of movies from database.
	 */
	@GetMapping(value = "/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		return new ResponseEntity<List<Movie>>(movieRepository.findAll(), HttpStatus.OK);

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

	@PostMapping(value = "/newMovieFromForm")
	public String addNewMovieFromForm(@RequestParam("title") String title, @RequestParam("length") int length,
			@RequestParam("description") String descrpition, @RequestParam("image") MultipartFile imageFile,
			@RequestParam("movie") MultipartFile movieFile) {
		Movie movieFound = movieRepository.findByTitle(title);
		if (movieFound == null) {
			String imageFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
			String movieFileName = StringUtils.cleanPath(movieFile.getOriginalFilename());
			try {
				Path pathImage = Paths.get(UPLOAD_DIR_IMAGE + imageFileName);
				System.out.println(pathImage.toAbsolutePath());
				Files.copy(imageFile.getInputStream(), pathImage, StandardCopyOption.REPLACE_EXISTING);
				Path pathMovie = Paths.get(UPLOAD_DIR_MOVIE + movieFileName);
				Files.copy(movieFile.getInputStream(), pathMovie, StandardCopyOption.REPLACE_EXISTING);

				Movie movie = new Movie();
				movie.setTitle(title);
				movie.setLength(length);
				movie.setDescription(descrpition);
				movie.setPathToImage(imageFileName);
				movie.setPathToMovie(movieFileName);
				movieRepository.save(movie);

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/moviesUI";
		} else {
			return "Movie already exist";

		}
	}

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
		model.addAttribute("firstMovie", listOfMovies.get(0));
		listOfMovies.remove(0);
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

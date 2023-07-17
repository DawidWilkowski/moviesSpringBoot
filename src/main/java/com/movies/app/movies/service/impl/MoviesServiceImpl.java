package com.movies.app.movies.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.repository.MovieRepository;
import com.movies.app.movies.service.MoviesService;

@Service
@RequiredArgsConstructor // generate constructor with movieRepository field autowired
public class MoviesServiceImpl implements MoviesService {

	private final String UPLOAD_DIR_IMAGE = "target/classes/static/images/poster/";
	private final String UPLOAD_DIR_MOVIE = "target/classes/static/moviesFolder/";


	private final MovieRepository movieRepository;

	@Override
	public ResponseEntity<List<Movie>> findAll() {
		return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Movie> getMovieById(Long id) {
		return movieRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<String> deleteMovieById(Long id) {
		if (movieRepository.existsById(id)) {
			movieRepository.deleteById(id);
			return new ResponseEntity<>("Successfully deleted movie", HttpStatus.OK);
		}
		return new ResponseEntity<>("No movie with id: " + id, HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<String> addNewMovie(Movie movie) {
		Movie movieFound = movieRepository.findByTitle(movie.getTitle());
		if (movieFound == null) {
			movieRepository.save(movie);
			return new ResponseEntity<>("Successfully added new movie", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Movie already exist", HttpStatus.CONFLICT);

		}
	}



	@Override
	public String addNewMovieFromForm(String title, int length, String description, MultipartFile imageFile,
									  MultipartFile movieFile) {
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
				movie.setDescription(description);
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

}

package com.movies.app.movies.repository;

import com.movies.app.movies.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class MoviesRepositoryTest {

    private MovieRepository movieRepository;
    @Autowired
    public MoviesRepositoryTest(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Test
    public void saveCreatedMovieTest(){
        Movie movie = Movie.builder()
                .title("testTitle").length(999)
                .pathToImage("testPathToImage")
                .description("testDesc")
                .pathToMovie("testPathToMovie").build();

        Movie savedMovie = movieRepository.save(movie);

        Assertions.assertNotNull(savedMovie);
    }
    @Test
    public void getMovieByIdTest(){
        Movie movie = Movie.builder()
                .title("testTitle").length(999)
                .pathToImage("testPathToImage")
                .description("testDesc")
                .pathToMovie("testPathToMovie").build();

        movieRepository.save(movie);

        Optional<Movie> foundMovie  = movieRepository.findById(movie.getId());

        Assertions.assertNotNull(foundMovie);
    }
    @Test
    public void deleteMovieByIdTest(){
        Movie movie = Movie.builder()
                .title("testTitle").length(999)
                .pathToImage("testPathToImage")
                .description("testDesc")
                .pathToMovie("testPathToMovie").build();

        movieRepository.save(movie);
        movieRepository.deleteById(movie.getId());

        Optional<Movie> foundMovie  = movieRepository.findById(movie.getId());

        assertThat(foundMovie.isEmpty());
    }
    @Test
    public void getAllMoviesTest(){
        List<Movie> movieList  = movieRepository.findAll();
        Assertions.assertNotNull(movieList);
    }

    @Test
    public void getMovieByTitleTest(){
        Movie movie = Movie.builder()
                .title("testTitle").length(999)
                .pathToImage("testPathToImage")
                .description("testDesc")
                .pathToMovie("testPathToMovie").build();

        movieRepository.save(movie);

        Movie foundMovie  = movieRepository.findByTitle(movie.getTitle());

        Assertions.assertNotNull(foundMovie);
    }

}

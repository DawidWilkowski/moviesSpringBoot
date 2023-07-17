package com.movies.app.movies.repository;

import com.movies.app.movies.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class MoviesRepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    @Test
    public void movieRepositorySaveMovieTest(){

        Movie movie = Movie.builder().id(null)
                .title("testTitle").length(999)
                .pathToImage("testPathToImage")
                .description("testDesc")
                .pathToMovie("testPathToMovie").build();
        Movie savedMovie = movieRepository.save(movie);
        Assertions.assertNotNull(savedMovie);

    }
}

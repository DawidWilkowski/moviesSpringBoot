package com.movies.app.movies.service;

import com.movies.app.movies.entity.Movie;
import com.movies.app.movies.repository.MovieRepository;
import com.movies.app.movies.repository.MoviesRepositoryTest;
import com.movies.app.movies.service.impl.MoviesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoviesServiceTest {

    @InjectMocks
    private MoviesServiceImpl moviesService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    public void getAllMoviesTest(){
        Movie movie = Movie.builder()
                .title("testTitle").length(999)
                .pathToImage("testPathToImage")
                .description("testDesc")
                .pathToMovie("testPathToMovie").build();
        List<Movie> list = new ArrayList<>();
        list.add(movie);

        when(movieRepository.findAll()).thenReturn(list);
        assertEquals(list, moviesService.findAll().getBody());
    }
}

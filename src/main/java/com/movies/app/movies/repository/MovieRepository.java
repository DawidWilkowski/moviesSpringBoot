package com.movies.app.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.app.movies.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}

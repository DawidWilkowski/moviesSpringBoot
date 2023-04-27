package com.movies.app.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.app.movies.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
}

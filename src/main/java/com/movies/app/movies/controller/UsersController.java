package com.movies.app.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.movies.app.movies.repository.UserRepository;

@Controller
public class UsersController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/usersUI")
	String users(Model model) {
		model.addAttribute("listUsers", userRepository.findAll());
		return "users";
	}
}

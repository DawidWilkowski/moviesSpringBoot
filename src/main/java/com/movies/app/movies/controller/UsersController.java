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

//	@GetMapping("/login")
//	String addMovie(Model model) {
//		model.addAttribute("user", new User());
//		return "login";
//	}
//
//	@PostMapping(value = "/loginForm")
//	public String loginForm(@RequestParam("userName") String userName, @RequestParam("userPassword") String password) {
//
//		if (userValidator(userName, password)) {
//			return "index";
//		} else {
//			return "redirect:/login";
//		}
//
//	}
//
//	private boolean userValidator(String userName, String password) {
//		User userToCheck = userRepository.findByUserName(userName); // might be null
//		if (userToCheck != null) {
//			if (password == userToCheck.getUserPassword()) {
//				return true;
//			}
//		}
//		return false;
//	}
}

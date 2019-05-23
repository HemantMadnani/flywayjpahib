package co.in.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import co.in.spring.model.User;
import co.in.spring.service.UserService;

@Controller
public class ActionController {

	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public ModelAndView loadHome() {

		System.out.println("Hi");
		final User user = new User();
		user.setEmail("admin@gmail.com");
		user.setFullName("Admin");
		user.setPassword("1234");
		user.setActive(false);
		userService.addUser(user);
		return new ModelAndView("home");
	}

	@GetMapping("/")
	public ModelAndView loadTest() {

		System.out.println("Hi");
		return new ModelAndView("home");
	}

}

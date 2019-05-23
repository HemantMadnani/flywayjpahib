package co.in.spring.service;

import java.util.List;

import co.in.spring.model.User;

public interface UserService {

	public List<User> getAll();

	public User addUser(User user);
}

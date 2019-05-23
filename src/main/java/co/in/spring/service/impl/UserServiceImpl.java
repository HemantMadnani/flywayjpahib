package co.in.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.spring.dao.UserDao;
import co.in.spring.model.User;
import co.in.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public List<User> getAll() {

		return userDao.findAll();
	}

	@Override
	@Transactional
	public User addUser(final User user) {

		return userDao.save(user);
	}

}

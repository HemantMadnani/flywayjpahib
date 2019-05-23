package co.in.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.in.spring.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}

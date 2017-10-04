package com.github.philipealves.heimdall.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.philipealves.heimdall.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

	public User findByUsername(String username);

}

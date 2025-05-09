package com.example.brainstromai.repository;

import com.example.brainstromai.model.db.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}

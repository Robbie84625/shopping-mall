package com.robbie.shoppingmall.dao;

import com.robbie.shoppingmall.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
  Boolean existsByEmail(String email);

  Users findByEmail(String email);
}

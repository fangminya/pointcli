package com.foodchain.repository;

import com.foodchain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends JpaRepository<User, String> {

    User findByUserName(String userName);

}

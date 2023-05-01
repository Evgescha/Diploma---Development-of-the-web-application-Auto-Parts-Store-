package com.hescha.autochapterstore.repository;

import com.hescha.autochapterstore.model.Order;
import com.hescha.autochapterstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByOrdersContains(Order orders);
}

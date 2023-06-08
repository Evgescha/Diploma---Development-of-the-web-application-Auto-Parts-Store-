package com.hescha.autochapterstore.service;

import com.hescha.autochapterstore.model.Order;
import com.hescha.autochapterstore.model.Role;
import com.hescha.autochapterstore.model.User;
import com.hescha.autochapterstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService extends CrudService<User> implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository repository;
    private final RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       RoleService roleService) {
        super(repository);
        this.repository = repository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername {}", username);
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь " + username + " не был найден в базе");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of());
    }


    public User update(Long id, User entity) {
        User read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity User not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(User entity, User read) {
        read.setUsername(entity.getUsername());
        read.setPassword(entity.getPassword());
        read.setFirstname(entity.getFirstname());
        read.setLastname(entity.getLastname());
        read.setEmail(entity.getEmail());
        read.setImage(entity.getImage());
        read.setAddress(entity.getAddress());
        read.setRoles(entity.getRoles());
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User findByOrdersContains(Order orders) {
        return repository.findByOrdersContains(orders);
    }

    public boolean registerNew(User entity) {
        Role read = roleService.read(1);
        entity.getRoles().add(read);
        log.info("registerNew {}", entity);
        if (repository.findByUsername(entity.getUsername()) != null) {
            return false;
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        try {
            create(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
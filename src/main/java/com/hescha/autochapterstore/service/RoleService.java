package com.hescha.autochapterstore.service;

import com.hescha.autochapterstore.model.Role;
import com.hescha.autochapterstore.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends CrudService<Role> {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Role update(Long id, Role entity) {
        Role read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity Role not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(Role entity, Role read) {
        read.setRole(entity.getRole());
    }
}

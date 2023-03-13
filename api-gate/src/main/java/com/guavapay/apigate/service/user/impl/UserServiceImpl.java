package com.guavapay.apigate.service.user.impl;

import com.guavapay.apigate.exception.EmailAlreadyExistsException;
import com.guavapay.apigate.exception.UserAlreadyExistsException;
import com.guavapay.datalib.user.UserCreateRequest;
import com.guavapay.apigate.model.Role;
import com.guavapay.apigate.model.Roles;
import com.guavapay.apigate.model.User;
import com.guavapay.apigate.repository.RoleRepository;
import com.guavapay.apigate.repository.UserRepository;
import com.guavapay.apigate.service.user.UserService;
import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import com.guavapay.datalib.user.UserDetailsResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, AmqpTemplate amqpTemplate) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User register(UserCreateRequest request) {
        validateUser(request);

        Role roleUser = roleRepository.findByName(Roles.USER.getLabel());
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(userRoles);
        user.setCreated(LocalDateTime.now());

        return repository.save(user);
    }

    @Override
    public void createDetails(UserDetailServiceCreateRequest request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isEmpty()) {
            throw new UserAlreadyExistsException();
        }

        amqpTemplate.convertSendAndReceive("user.create", request);
    }

    @Override
    public UserDetailsResponse getDetails(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new UserAlreadyExistsException();
        }

        return (UserDetailsResponse)amqpTemplate.convertSendAndReceive("user.getById", id);
    }

    private void validateUser(UserCreateRequest request) {
        User user = repository.findByUsername(request.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        user = repository.findByEmail(request.getEmail());
        if (user != null) {
            throw new EmailAlreadyExistsException();
        }
    }
}

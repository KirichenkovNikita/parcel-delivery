package com.guavapay.apigate.service.courier.impl;

import com.guavapay.apigate.model.Role;
import com.guavapay.apigate.model.Roles;
import com.guavapay.apigate.model.User;
import com.guavapay.apigate.repository.RoleRepository;
import com.guavapay.apigate.repository.UserRepository;
import com.guavapay.apigate.service.courier.CourierService;
import com.guavapay.apigate.utils.UserValidator;
import com.guavapay.datalib.courier.CourierResponse;
import com.guavapay.datalib.courier.CreateCourierRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService {
    private static final String TYPE_IS_EMPTY = "Courier type cannot be empty";
    private final AmqpTemplate amqpTemplate;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public CourierServiceImpl(AmqpTemplate amqpTemplate, UserValidator userValidator, UserRepository userRepository, RoleRepository roleRepository) {
        this.amqpTemplate = amqpTemplate;
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void create(CreateCourierRequest request) {
        userValidator.checkUser(request.getUser());
        userValidator.checkUser(request.getCreator());
        if (request.getStatus() == null) {
            throw new IllegalArgumentException(TYPE_IS_EMPTY);
        }

        boolean success = (boolean) amqpTemplate.convertSendAndReceive("courier.create", request);
        if (success) {
            Role roleCourier = roleRepository.findByName(Roles.COURIER.getLabel());

            Optional<User> optionalUser = userRepository.findById(request.getUser());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<Role> roles = user.getRoles();
                roles.add(roleCourier);
                user.setRoles(roles);
                userRepository.save(user);
            }
        }
    }

    @Override
    public List<CourierResponse> getAll() {
        return (List<CourierResponse>)amqpTemplate.convertSendAndReceive("courier.getAll", 0);
    }
}

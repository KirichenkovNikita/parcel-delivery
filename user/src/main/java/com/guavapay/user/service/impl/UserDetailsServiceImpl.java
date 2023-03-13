package com.guavapay.user.service.impl;

import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import com.guavapay.datalib.user.UserDetailsResponse;
import com.guavapay.user.model.UserDetails;
import com.guavapay.user.repository.UserDetailsRepository;
import com.guavapay.user.service.UserDetailService;
import com.guavapay.user.utils.DataConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailService {
    private final UserDetailsRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(UserDetailServiceCreateRequest request) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(request.getId());
        userDetails.setFirstName(request.getFirstName());
        userDetails.setLastName(request.getLastName());
        userDetails.setIsActive(request.getActive());

        repository.save(userDetails);
    }

    @Override
    public UserDetailsResponse get(Long user_id) {
        Optional<UserDetails> details = repository.findById(user_id);
        return details.map(DataConvertor::getUserResponse).orElseGet(UserDetailsResponse::new);

    }
}

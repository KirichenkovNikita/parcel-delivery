package com.guavapay.apigate.service.user;

import com.guavapay.apigate.model.User;
import com.guavapay.datalib.user.UserCreateRequest;
import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import com.guavapay.datalib.user.UserDetailsResponse;

public interface UserService {
    User findByUsername(String username);
    User register(UserCreateRequest user);
    void createDetails(UserDetailServiceCreateRequest request);
    UserDetailsResponse getDetails(Long id);
}

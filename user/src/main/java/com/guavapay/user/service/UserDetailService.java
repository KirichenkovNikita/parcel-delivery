package com.guavapay.user.service;

import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import com.guavapay.datalib.user.UserDetailsResponse;

public interface UserDetailService {
    void create(UserDetailServiceCreateRequest request);

    UserDetailsResponse get(Long user_id);
}

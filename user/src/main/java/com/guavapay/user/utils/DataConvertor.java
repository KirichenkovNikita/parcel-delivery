package com.guavapay.user.utils;

import com.guavapay.datalib.user.UserDetailsResponse;
import com.guavapay.user.model.UserDetails;

public class DataConvertor {
    public static UserDetailsResponse getUserResponse(UserDetails user) {
        UserDetailsResponse result = new UserDetailsResponse();
        result.setId(user.getId());
        result.setActive(user.getIsActive());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());

        return result;
    }
}

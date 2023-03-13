package com.guavapay.user.rabbit;

import com.guavapay.datalib.user.UserDetailServiceCreateRequest;
import com.guavapay.datalib.user.UserDetailsResponse;
import com.guavapay.user.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableRabbit
public class UserListener {
    private static final String QUEUE_CREATE = "user.create";
    private static final String QUEUE_GET_BY_ID = "user.getById";
    private final UserDetailService service;

    @Autowired
    public UserListener(UserDetailService service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE_CREATE)
    public boolean create(UserDetailServiceCreateRequest message){
        try {
            service.create(message);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    @RabbitListener(queues = QUEUE_GET_BY_ID)
    public UserDetailsResponse create(Long id){
        try {
            return service.get(id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new UserDetailsResponse();
        }
    }
}

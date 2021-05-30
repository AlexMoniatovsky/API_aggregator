package com.elevate.api.aggregator.controller;

import com.elevate.api.aggregator.chain.user.CreateBillingUserHandler;
import com.elevate.api.aggregator.chain.user.CreatePersonHandler;
import com.elevate.api.aggregator.chain.user.context.CreateUserContext;
import com.elevate.api.aggregator.controller.model.CreateUserRequest;
import com.elevate.api.aggregator.controller.model.CreateUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @PostMapping("/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        CreateUserContext createUserContext = new CreateUserContext(request);
        new CreatePersonHandler(
            new CreateBillingUserHandler()
        ).runChain(createUserContext);
        return createUserContext.finalizeChain();
    }

}

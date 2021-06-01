package com.elevate.api.aggregator.controller;

import static com.elevate.api.aggregator.router.RouterNames.CREATE_USER_ROUTER;

import com.elevate.api.aggregator.chain.user.CreateBillingUserHandler;
import com.elevate.api.aggregator.chain.user.CreatePersonHandler;
import com.elevate.api.aggregator.chain.user.context.CreateUserContext;
import com.elevate.api.aggregator.controller.model.CreateUserRequest;
import com.elevate.api.aggregator.controller.model.CreateUserResponse;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @EndpointInject(value = CREATE_USER_ROUTER)
    private ProducerTemplate restProducer;

    @PostMapping("/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        CreateUserContext createUserContext = new CreateUserContext(request);
        new CreatePersonHandler(
            new CreateBillingUserHandler()
        ).runChain(createUserContext);
        return createUserContext.finalizeChain();
    }

    @PostMapping("/camel/user")
    public CreateUserResponse createUserCamel(@RequestBody CreateUserRequest request) {
        return restProducer.requestBody(request, CreateUserResponse.class);
    }



}

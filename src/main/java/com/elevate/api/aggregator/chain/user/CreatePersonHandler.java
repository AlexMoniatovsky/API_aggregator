package com.elevate.api.aggregator.chain.user;

import static com.elevate.api.aggregator.client.PersonClient.CREATE_PERSON_RESOURCE;

import com.elevate.api.aggregator.chain.ApiCallHandler;
import com.elevate.api.aggregator.chain.user.context.CreateUserContext;
import com.elevate.api.aggregator.client.BaseClient;
import com.elevate.api.aggregator.client.model.person.CreatePersonRequest;
import com.elevate.api.aggregator.client.model.person.Person;

import java.util.concurrent.CompletableFuture;

public class CreatePersonHandler extends ApiCallHandler<CreateUserContext> {

    public CreatePersonHandler(ApiCallHandler<CreateUserContext> next) {
        super(next);
    }

    @Override
    public void handle(CreateUserContext createUserContext) {
        createUserContext.setPersonFuture(
            CompletableFuture.supplyAsync(() ->
                BaseClient.post(
                    CreatePersonRequest.builder()
                                       .name(createUserContext.getRequest().getName())
                                       .build(),
                    CREATE_PERSON_RESOURCE,
                    Person.class
                )
            )
        );
    }
}

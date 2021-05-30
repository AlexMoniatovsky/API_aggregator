package com.elevate.api.aggregator.chain.user;

import static com.elevate.api.aggregator.client.BillingClient.CREATE_BILLING_USER_RESOURCE;

import com.elevate.api.aggregator.chain.ApiCallHandler;
import com.elevate.api.aggregator.chain.user.context.CreateUserContext;
import com.elevate.api.aggregator.client.BaseClient;
import com.elevate.api.aggregator.client.model.billing.BillingUser;
import com.elevate.api.aggregator.client.model.person.CreatePersonRequest;

import java.util.concurrent.CompletableFuture;

public class CreateBillingUserHandler extends ApiCallHandler<CreateUserContext> {

    @Override
    public void handle(CreateUserContext createUserContext) {
        createUserContext.setBillingUserFuture(
            CompletableFuture.supplyAsync(() ->
                BaseClient.post(
                    CreatePersonRequest.builder()
                                       .name(createUserContext.getRequest().getName())
                                       .build(),
                    CREATE_BILLING_USER_RESOURCE,
                    BillingUser.class
                )
            )
        );
    }

}

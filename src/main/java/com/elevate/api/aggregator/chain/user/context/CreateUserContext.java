package com.elevate.api.aggregator.chain.user.context;

import com.elevate.api.aggregator.client.model.billing.BillingUser;
import com.elevate.api.aggregator.client.model.person.Person;
import com.elevate.api.aggregator.controller.model.CreateUserRequest;
import com.elevate.api.aggregator.controller.model.CreateUserResponse;
import com.elevate.api.aggregator.exception.InternalException;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
@Setter
public class CreateUserContext extends BaseContext<CreateUserRequest, CreateUserResponse> {

    private CompletableFuture<Person> personFuture;
    private CompletableFuture<BillingUser> billingUserFuture;

    public CreateUserContext(CreateUserRequest createUserRequest) {
        super(createUserRequest);
    }


    @Override
    public CreateUserResponse finalizeChain() {
        try {
            return CreateUserResponse.builder()
                                     .id(personFuture.get().getId())
                                     .billingId(billingUserFuture.get().getId())
                                     .build();
        } catch (InterruptedException e) {
            throw new InternalException("InterruptedException occurred while async handling user creation chain");
        } catch (ExecutionException e) {
            throw new InternalException("ExecutionException occurred while async handling user creation chain", e.getCause());
        }
    }
}

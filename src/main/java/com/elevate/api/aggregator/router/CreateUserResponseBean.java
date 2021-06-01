package com.elevate.api.aggregator.router;

import static com.elevate.api.aggregator.router.RouterPropertiesNames.USER_CREATE_RESPONSE;

import com.elevate.api.aggregator.client.model.billing.BillingUser;
import com.elevate.api.aggregator.client.model.person.CreatePersonRequest;
import com.elevate.api.aggregator.client.model.person.Person;
import com.elevate.api.aggregator.controller.model.CreateUserRequest;
import com.elevate.api.aggregator.controller.model.CreateUserResponse;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperty;

public class CreateUserResponseBean {

    public void mergeUserResponseWithPerson(@ExchangeProperty(USER_CREATE_RESPONSE)CreateUserResponse userResponse,
          @Body Person person) {
        userResponse.setId(person.getId());
    }

    public void mergeUserResponseWithBillingUser(@ExchangeProperty(USER_CREATE_RESPONSE)CreateUserResponse userResponse,
          @Body BillingUser billingUser) {
        userResponse.setBillingId(billingUser.getId());
        userResponse.setName(billingUser.getName());
    }

    public CreatePersonRequest buildPersonRequest(@Body CreateUserRequest userRequest) {
        return CreatePersonRequest.builder().name(userRequest.getName()).build();
    }
}

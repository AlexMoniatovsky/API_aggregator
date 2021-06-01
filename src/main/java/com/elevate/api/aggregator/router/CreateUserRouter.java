package com.elevate.api.aggregator.router;

import static com.elevate.api.aggregator.router.RouterNames.CREATE_BILLING_ROUTER;
import static com.elevate.api.aggregator.router.RouterNames.CREATE_PERSON_ROUTER;
import static com.elevate.api.aggregator.router.RouterNames.CREATE_USER_ROUTER;
import static com.elevate.api.aggregator.router.RouterPropertiesNames.USER_CREATE_REQUEST;
import static com.elevate.api.aggregator.router.RouterPropertiesNames.USER_CREATE_RESPONSE;

import com.elevate.api.aggregator.client.model.billing.BillingUser;
import com.elevate.api.aggregator.client.model.person.CreatePersonRequest;
import com.elevate.api.aggregator.client.model.person.Person;
import com.elevate.api.aggregator.controller.model.CreateUserResponse;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Predicate;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.HttpException;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        //CreateUserRequest
        from(CREATE_USER_ROUTER)
            .setProperty(USER_CREATE_REQUEST, body())//CreateUserRequest
            .setProperty(USER_CREATE_RESPONSE, CreateUserResponse::new)
            .multicast(AggregationStrategies.useOriginal(), true)
                .to(CREATE_BILLING_ROUTER)
                .to(CREATE_PERSON_ROUTER)
            .end()
            .setBody(exchangeProperty(USER_CREATE_RESPONSE));


        from(CREATE_BILLING_ROUTER)//CreateUserRequest
              .bean(CreateUserResponseBean.class)
              .marshal()
              .json(JsonLibrary.Jackson)
              .setHeader("CamelHttpMethod", constant("GET"))
              .to("https://alex-test1.free.beeceptor.com/billing/user")
                .unmarshal()
                .json(JsonLibrary.Jackson, Person.class)
              .bean(CreateUserResponseBean.class);


        from(CREATE_PERSON_ROUTER)
              .bean(CreateUserResponseBean.class)
              .marshal()
              .json(JsonLibrary.Jackson)
              .setHeader("CamelHttpMethod", constant("GET"))
              .to("https://alex-test1.free.beeceptor.com/billing/user")
                .unmarshal()
                .json(JsonLibrary.Jackson, BillingUser.class)
              .bean(CreateUserResponseBean.class);
    }
}
